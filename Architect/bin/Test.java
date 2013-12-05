
public class Test {
	static {
			System.loadLibrary("JDll");
		} 
	
	public native static void showVoidContent();
	
	public native static String showReturnContent();
	
	public native static String getContent(String bookName, int bookId, long bookTime, double bookPrice);
	
	public native static String getOneContent(String one, String two);
	
	public native static double getTwoDouble(double one, double two, String three);

	
	public static void main(String[] args)
	{
		Test tt = new Test();
		Test.showVoidContent();
		Test.showReturnContent();
		String str = Test.getOneContent("aaa", "bbb");
		System.out.println(str);
		
	}
}
