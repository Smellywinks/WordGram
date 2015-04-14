package minyon;

public class StringTable {
	public int capacity;
	public int size;
	public String[] table;
	
	//initialize with capacity of 100
	public StringTable(){
		this.size = 0;
		this.capacity = 100;
		this.table = new String[this.capacity];
	}

	//initialize with specialized capacity
	public StringTable(int cap){
		this.size = 0;
		this.capacity = cap;
		this.table = new String[cap];
	}
	
	//hash the current string 
	public int hash(String s, int cap){
			int p = 13;
			int h = 0;
			for(int i = 0; i < s.length();){
				h = p * h + s.charAt(i);
				i++;
			}
			h %= cap;
			if(h < 0) h += cap;
			return h;
	}
	
	//our table needs to be bigger
	public void rehash(){
		int newcap = (int)(1.3 * this.capacity);
		String[] newTable = new String[newcap];
		
		//rehash items in our table
		for(int j = 0; j < this.table.length; j++){
			if(this.table[j] != null){
				int k = hash(this.table[j], newcap);
				while(newTable[k] != null){
					k++;
					k %= newcap;
				}
				newTable[k] = this.table[j];
			}
		}
		this.capacity = newcap;
		this.table = newTable;
	}
	
	//insert the string into our table (hash it first)
	public void insert(String s){
		if(this.size/this.capacity > 0.5F){
			rehash();
		}
		int i = hash(s, this.capacity);

		//check for collisions
		while((this.table[i] != null) && (!s.equals(this.table[i]))){
			i++;
			i %= this.capacity;
		}
		this.table[i] = s;
		this.size++;
	}
	
	//does our table contain the string?
	public boolean contains(String s){
		int i = hash(s, this.capacity);
		for(;;){
			if(this.table[i] == null) return false;
			if(this.table[i].equals(s)) return true;
			i++;
			i %= this.capacity;
		}
	}
}
