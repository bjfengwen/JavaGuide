package example;
public enum Color{  
    RED,BLUE,BLACK,YELLOW,GREEN  ;
    public static void main(String[] args) {
		Color color[]=Color.values();
		Color color2=Color.valueOf("BLUE");
		System.out.println(color2);
	}
}  