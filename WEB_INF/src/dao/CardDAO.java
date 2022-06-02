package dao;

import static db.JdbcUtil.*;//jdbc 연결 안 하고 작성
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import vo.CardBean;

public class CardDAO {

	DataSource ds;
	Connection con;
	private static CardDAO cardDAO;

	private CardDAO() {}


	//싱글톤 패턴으로 cardDAO 객체가 null이면 새로운 객체를 생성 후 반환
	public static CardDAO getInstance() {
		if(cardDAO == null) {
			cardDAO = new CardDAO();
		}
		return cardDAO;
	}

	//접근자는 여기서 가지고 오지 않음
	public void setCon(Connection con) {
		this.con = con;
	}

	//글 목록보기
	//vo 객체 등 여러 객체와 클래스가 없는 관계로 테스트 불가능.
	public ArrayList<CardBean> selectCardList(/* 파라미터*/){
		//pstmt과 rs 초기화
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//직급과 사원번호 순으로 오름차순 정렬
		String card_list_sql="select * from bcard order by pos_num, emp_num asc";
		//vo 객체에 담을 arraylist 초기화
		ArrayList<CardBean> cardList = new ArrayList<CardBean>();
		//vo 객체 초기화
		CardBean card = null;


		//오류처리 및 설정자를 이용해서, 접근자로 불러온 데이터를 저장.

		//vo객체로 인해 직렬화 불필요.
		try {
			//정렬된 목록 불러오기
			pstmt = con.prepareStatement(board_list_sql);
			rs = pstmt.executeQuery();

			//card 객체에 더이상 검색되는 데이터가 없을 때까지 넣음.
			while(rs.next()) {
				//새로운 vo객체 생성
				card = new CardBean;
				card.setEMP_NUM(rs.getInt("EMP_NUM"));
				card.setNAME_KOR(rs.getString("NAME_KOR"));
				card.setSOC_NUM(rs.getInt("SOC_NUM"));
				card.setDEP_NUM(rs.getInt("DEP_NUM"));
				card.setPOS_NUM(rs.getInt("POS_NUM"));
				card.setMOBILE(rs.getInt("MOBILE"));
				card.setPHONE(rs.getInt("PHONE"));
				card.setFAX(rs.getInt("FAX"));
				card.setEMAIL(rs.getString("EMAIL"));
				card.setD_ENTRY(rs.getDate("D_ENTRY"));
				card.setD_RESIGN(rs.getDate("D_RESIGN"));
				cardList.add(card);
			}
		}catch(Exception e) {
			System.out.println("getCardList 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
	return cardList;
		
	}
	
}
