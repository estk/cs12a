class YummyCritter {
    public String name;
    public int position;
    
    public YummyCritter(int pos) {
        name = "critter" + pos;
        position = pos;
    }

    public String toString() { return name; }
}
