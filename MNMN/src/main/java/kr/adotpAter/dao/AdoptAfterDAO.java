package kr.adotpAter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.adoptAfter.vo.AdoptAfterVO;
import kr.util.DBUtil;

public class AdoptAfterDAO {

	private static AdoptAfterDAO instance = new AdoptAfterDAO();
	
	public static AdoptAfterDAO getInstance() {
		return instance;
	}
	
	private AdoptAfterDAO() {}
	
	//�Ծ��ı� �Խ��� �� ���
	public void insertAdoptAfter(AdoptAfterVO adoptAfter)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String sql = null;
		int afterPetNum = 0;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			//��� ���ϱ�
			sql = "select adopt_pet_num from adopt where adopt_member_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, adoptAfter.getAfter_member_num());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				afterPetNum = rs.getInt(1);
			}
			
			//�� ����ϱ�
			sql = "insert into adopt_after(after_num, after_pet_num, after_title, after_content, after_member_num, after_photo)"
				+ "values(adopt_after_seq.nextval,?,?,?,?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, afterPetNum);
			pstmt2.setString(2, adoptAfter.getAfter_title());
			pstmt2.setString(3, adoptAfter.getAfter_content());
			pstmt2.setInt(4, adoptAfter.getAfter_member_num());
			pstmt2.setString(5, adoptAfter.getAfter_photo());
			pstmt2.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
	}
	//�Ծ��ı� �Խ��� �� ���ڵ� ��
	public int getAfterBoardCount()throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try{
			conn = DBUtil.getConnection();
			sql = "select count(*) from adopt_after";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.getConnection();
		}
		
		return count;
	}
	
	//�Ծ��ı� �Խ��� �� ���
	public List<AdoptAfterVO> getAfterListBoard(int start, int end)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdoptAfterVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "select * from (select a.*, rownum rnum from (select * from adopt_after a join member m on a.after_member_num=m.member_num order by after_num desc)a) where rnum >= ? and rnum <=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<AdoptAfterVO>();
			while(rs.next()) {
				AdoptAfterVO after = new AdoptAfterVO();
				after.setAfter_num(rs.getInt("after_num"));
				after.setAfter_pet_num(rs.getInt("after_pet_num"));
				after.setAfter_title(rs.getString("after_title"));
				after.setAfter_content(rs.getString("after_content"));
				after.setAfter_date(rs.getDate("after_date"));
				after.setAfter_member_num(rs.getInt("after_member_num"));
				after.setAfter_photo(rs.getString("after_photo"));
				after.setMember_id(rs.getString("member_id"));
				list.add(after);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//�Ծ��ı� �Խ��� �� ��
	public AdoptAfterVO getAfterBoard(int after_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdoptAfterVO after = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "select * from adopt_after a join member m on a.after_member_num=m.member_num where a.after_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, after_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				after = new AdoptAfterVO();
				after.setAfter_num(rs.getInt("after_num"));
				after.setAfter_pet_num(rs.getInt("after_pet_num"));
				after.setAfter_title(rs.getString("after_title"));
				after.setAfter_content(rs.getString("after_content"));
				after.setAfter_date(rs.getDate("after_date"));
				after.setAfter_member_num(rs.getInt("after_member_num"));
				after.setAfter_photo(rs.getString("after_photo"));
				after.setMember_id(rs.getString("member_id"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return after;
	}
	//�Ծ��ı� �Խ��� �� ����
	public void updateAfterBoard(AdoptAfterVO adoptAfter)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "update adopt_after set after_title=?,after_content=?,after_photo=? where after_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, adoptAfter.getAfter_title());
			pstmt.setString(2, adoptAfter.getAfter_content());
			pstmt.setString(3, adoptAfter.getAfter_photo());
			pstmt.setInt(4, adoptAfter.getAfter_num());
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	
	//�Ծ��ı� �Խ��� �� ����
	public void deleteAfterBoard(int after_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "delete from adopt_after where after_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, after_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}











