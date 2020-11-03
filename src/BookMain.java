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
			System.out.println("\n======메뉴 선택하기======");
			System.out.println("1.전체 정보 조회");
			System.out.println("2.조건 조회");
			System.out.println("3.정보 수정");
			System.out.println("4.정보 삭제");
			System.out.println("5.정보 추가");
			System.out.println("6.프로그램 종료");

			System.out.print(">> 메뉴 번호 입력 : ");
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
					
			case 6 : System.out.println("프로그램 종료");
					System.exit(0);
					break;
					
			default : 
				System.out.println("1~6사이의 번호만 입력 가능");  
				
			}
		}//while 
		
	}//init 
	
	public void getBookBySearch(){
		
		String column = null;
		
		System.out.print("제목:1  저자:2  출판사:3  번호입력>>");
		int search_num = sc.nextInt();
		
		switch(search_num) {
		case 1: System.out.print("조회할 제목 : "); // 리
				column = "title";
				break;
		
		case 2: System.out.print("조회할 저자 : "); // 정
				column = "author"; 
				break;
				
		case 3: System.out.print("조회할 출판사 : "); // 출판
				column = "publisher";
				break;
				
		default : System.out.println("1~3사이의 번호만 입력 가능");
				return;
		}
		
		String search_word = sc.next(); // 리, 정, 출판 
		
		ArrayList<BookBean> lists = dao.getBookBySearch(column,search_word); // author,정 
		showBook(lists);
		
//		select * from book where title like '%리%';
//		select * from book where publisher like '%리%';
	}
	
	private void deleteData() {
		System.out.print("삭제할 번호 입력 : ");
		int no = sc.nextInt(); 
		int cnt = dao.deleteData(no);
		
		if(cnt <= 0)
			System.out.println("delete 실패");
		else
			System.out.println("delete 성공");
		
	}

	private void updateData() {
		
		int no=0, price = 0;
		String title = null, author=null, publisher = null, pub_day=null   ;	

		int cnt = -1 ;
		BookBean book = new BookBean();

		System.out.print("수정할 번호 입력 : ");
		no = sc.nextInt(); 

		System.out.print("책제목 입력 : ");
		title = sc.next(); 

		System.out.print("저자 입력 : ");
		author = sc.next(); 

		System.out.print("출판사 입력 : ");
		publisher = sc.next(); 

		System.out.print("가격 입력 : ");
		price = sc.nextInt(); 

		System.out.print("출간일(yyyy/mm/dd 형식으로 입력하세요.) : ");
		pub_day = sc.next(); 	 

		book.setNo(no);
		book.setTitle(title) ;    
		book.setAuthor(author) ; 
		book.setPublisher(publisher) ; 	
		book.setPrice(price); 
		book.setPub_day(pub_day);

		cnt = dao.updateData(book); 
		
		if(cnt <= 0)
			System.out.println("update 실패");
		else
			System.out.println("update 성공");
		
		
	}

	public void insertData(){
		int price = 0;
		String title = null, author=null, publisher = null, pub_day=null   ;	

		int result = -1 ;
		BookBean book = new BookBean();

		System.out.println("번호는 시퀀스로 입력됩니다(생략)");
		System.out.print("책제목 입력 : ");
		title = sc.next(); 

		System.out.print("저자 입력 : ");
		author = sc.next(); 

		System.out.print("출판사 입력 : ");
		publisher = sc.next(); 

		System.out.print("가격 입력 : ");
		price = sc.nextInt();

		System.out.print("출간일 입력 : ");
		pub_day = sc.next(); 		

		book.setTitle(title) ;    
		book.setAuthor(author) ; 
		book.setPublisher(publisher) ; 	
		book.setPrice(price); 
		book.setPub_day(pub_day);
		int cnt = dao.insertData(book);
		
		if(cnt <= 0)
			System.out.println("insert 실패");
		else
			System.out.println("insert 성공");
		
	}
	
	
	
	
	
	
	public void showBook(ArrayList<BookBean> lists){
		System.out.println("총 " + lists.size() +"권 입니다.");
		String title = "번호\t" + "책제목\t" + "저자\t" + "출판사\t" + "가격\t" + "출간일\t"; 
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



