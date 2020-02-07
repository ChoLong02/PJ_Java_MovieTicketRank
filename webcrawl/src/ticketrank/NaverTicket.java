package ticketrank;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import domain.MovieDTO;
import persistence.MovieDAO;

public class NaverTicket {
	String base = "";
	int page = 1;      // 수집한 페지이수(1page=댓글10개)
	String url = "";
	int total = 0;     // 평점을 모두 더하는 변수
	String compare = ""; // 마지막 페이지 종료를 위한 변수
	MovieDAO mDao = new MovieDAO();
	
	// 네이버 영화 댓글(평점)을 수집하는 메서드
	public TicketDTO naverCrawler(String movie, String code) throws IOException {
		int count = 0;     // 전체 댓글수
		base = "https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code="+code+"&type=after&isActualPointWriteExecute=false&isMileageSubscriptionAlready=false&isMileageSubscriptionReject=false&page=";
		url = base+page;
		System.out.println("■■NAVER START■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		
		label:while(true) {
			Document doc = Jsoup.connect(url).get();
			Elements reply = doc.select("div.score_result > ul > li");
			
			int score = 0;
		    int regdate = 0;
			String writer = "";
			String content = "";
			// 평점 1건 수집
			for (int i = 0; i < reply.size(); i++) {
				score = Integer.parseInt(reply.get(i).select("div.star_score > em").text());
				content = reply.get(i).select(".score_reple > p").text();
				writer = reply.get(i).select(".score_reple dt em:first-child").text();
				regdate = Integer.parseInt(reply.get(i).select(".score_reple dt em:last-child").text().substring(0, 10).replace(".", ""));
				
				// 마지막 페이지 종료
				if(i == 0) {
			    	if(compare.equals(writer)) {
			    		break label;
			    	} else {
			    		compare = writer;
			    	}
			    }
				
				count++;
				// 누적 평점을 계산
				total += score; //total = total + score; 

				// 1건에 평점을 DTO에 담음
				MovieDTO mDto = new MovieDTO(movie, content, writer, score, "naver", regdate);
				
				// DTO에 담긴 1건의 평점을 DB에 저장
				mDao.addMovie(mDto);
				System.out.println("■■"+count+"건■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				System.out.println("영화: " + movie);
				System.out.println("평점: " + score);
				System.out.println("작성자: " + writer);
				System.out.println("내용: " + content);
				System.out.println("작성일자: " + regdate);
			}
			// 다음 페이지로 이동하기 위해 page+1 증가
			page = page + 1;
			// 다음 페이지로 이동할 URL 작성
			url = base + page;
		}
		
		System.out.println("■■NAVER END■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");

		// TicketMain으로 댓글수, 평점의 합을 return
		TicketDTO tDto = new TicketDTO(count, total);
		return tDto;
	}
}





















