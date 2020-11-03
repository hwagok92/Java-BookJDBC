import java.util.ArrayList;
import java.util.Scanner;

public class BookMain {
	
	BookDao dao = new BookDao();
	Scanner sc = new Scanner(System.in);
	BookMain(){
		init();
	}
	
	public void init() {
		while(true){
			System.out.println("\n======�޴� �����ϱ�======");
			System.out.println("1.��ü ���� ��ȸ");
			System.out.println("2.���� ��ȸ");
			System.out.println("3.���� ����");
			System.out.println("4.���� ����");
			System.out.println("5.���� �߰�");
			System.out.println("6.���α׷� ����");

			System.out.print(">> �޴� ��ȣ �Է� : ");
			int menu = sc.nextInt() ;
			
			switch(menu) {
			case 1 : ArrayList<BookBean> lists = dao.getAllBook();
					showBook(lists);
					break;
					   
			case 2 : getBookBySearch();  
					break;
					
			case 3 : updateData(); 
					break;
					
			case 4 : deleteData();
					break;
					
			case 5 : insertData();
					break;
					
			case 6 : System.out.println("���α׷� ����");
					System.exit(0);
					break;
					
			default : 
				System.out.println("1~6������ ��ȣ�� �Է� ����");  
				
			}
		}//while 
		
	}//init 
	
	public void getBookBySearch(){
		
		String column = null;
		
		System.out.print("����:1  ����:2  ���ǻ�:3  ��ȣ�Է�>>");
		int search_num = sc.nextInt();
		
		switch(search_num) {
		case 1: System.out.print("��ȸ�� ���� : "); // ��
				column = "title";
				break;
		
		case 2: System.out.print("��ȸ�� ���� : "); // ��
				column = "author"; 
				break;
				
		case 3: System.out.print("��ȸ�� ���ǻ� : "); // ����
				column = "publisher";
				break;
				
		default : System.out.println("1~3������ ��ȣ�� �Է� ����");
				return;
		}
		
		String search_word = sc.next(); // ��, ��, ���� 
		
		ArrayList<BookBean> lists = dao.getBookBySearch(column,search_word); // author,�� 
		showBook(lists);
		
//		select * from book where title like '%��%';
//		select * from book where publisher like '%��%';
	}
	
	private void deleteData() {
		System.out.print("������ ��ȣ �Է� : ");
		int no = sc.nextInt(); 
		int cnt = dao.deleteData(no);
		
		if(cnt <= 0)
			System.out.println("delete ����");
		else
			System.out.println("delete ����");
		
	}

	private void updateData() {
		
		int no=0, price = 0;
		String title = null, author=null, publisher = null, pub_day=null   ;	

		int cnt = -1 ;
		BookBean book = new BookBean();

		System.out.print("������ ��ȣ �Է� : ");
		no = sc.nextInt(); 

		System.out.print("å���� �Է� : ");
		title = sc.next(); 

		System.out.print("���� �Է� : ");
		author = sc.next(); 

		System.out.print("���ǻ� �Է� : ");
		publisher = sc.next(); 

		System.out.print("���� �Է� : ");
		price = sc.nextInt(); 

		System.out.print("�Ⱓ��(yyyy/mm/dd �������� �Է��ϼ���.) : ");
		pub_day = sc.next(); 	 

		book.setNo(no);
		book.setTitle(title) ;    
		book.setAuthor(author) ; 
		book.setPublisher(publisher) ; 	
		book.setPrice(price); 
		book.setPub_day(pub_day);

		cnt = dao.updateData(book); 
		
		if(cnt <= 0)
			System.out.println("update ����");
		else
			System.out.println("update ����");
		
		
	}

	public void insertData(){
		int price = 0;
		String title = null, author=null, publisher = null, pub_day=null   ;	

		int result = -1 ;
		BookBean book = new BookBean();

		System.out.println("��ȣ�� �������� �Էµ˴ϴ�(����)");
		System.out.print("å���� �Է� : ");
		title = sc.next(); 

		System.out.print("���� �Է� : ");
		author = sc.next(); 

		System.out.print("���ǻ� �Է� : ");
		publisher = sc.next(); 

		System.out.print("���� �Է� : ");
		price = sc.nextInt();

		System.out.print("�Ⱓ�� �Է� : ");
		pub_day = sc.next(); 		

		book.setTitle(title) ;    
		book.setAuthor(author) ; 
		book.setPublisher(publisher) ; 	
		book.setPrice(price); 
		book.setPub_day(pub_day);
		int cnt = dao.insertData(book);
		
		if(cnt <= 0)
			System.out.println("insert ����");
		else
			System.out.println("insert ����");
		
	}
	
	
	
	
	
	
	public void showBook(ArrayList<BookBean> lists){
		System.out.println("�� " + lists.size() +"�� �Դϴ�.");
		String title = "��ȣ\t" + "å����\t" + "����\t" + "���ǻ�\t" + "����\t" + "�Ⱓ��\t"; 
		System.out.println( title );
		
		for( BookBean bb : lists) {
			String result = bb.getNo()+"\t" + 
							bb.getTitle()+"\t" + 
							bb.getAuthor()+"\t" + 
							bb.getPublisher()+"\t" + 
							bb.getPrice()+"\t" + 
							bb.getPub_day()+"\t";
			
			System.out.println(result);
		}
		
		
	}//showBook
	
	public static void main(String[] args) {
		new BookMain();
	}

}



