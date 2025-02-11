public class Dessert {
    private static int numDesserts;
    private int flavor;
    private int price;
    Dessert(int flavor, int price)
    {
        this.flavor = flavor;
        this.price = price;
    }
    public void printDessert()
    {
        System.out.println(this.flavor + " " + this.price + " " + numDesserts);
    }
    public static void main(String[] args)
    {
        System.out.println("I love dessert!");
    }
}
