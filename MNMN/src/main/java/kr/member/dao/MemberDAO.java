package kr.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.member.vo.MemberVO;
import kr.util.DBUtil;

public class MemberDAO {
	//�̱��� ����
	private static MemberDAO instance = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return instance;
	}
	private MemberDAO() {}
	
	//ID �ߺ� üũ �� �α���
	public MemberVO checkMember(String id) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();

			sql = "SELECT * FROM member m LEFT OUTER JOIN member_detail d "
				+ "ON m.member_num = d.member_detail_num WHERE m.member_id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new MemberVO();
				member.setMember_num(rs.getInt("member_num"));
				member.setMember_id(rs.getString("member_id"));
				member.setMember_grade(rs.getInt("member_grade"));
				member.setMember_detail_pw(rs.getString("member_detail_pw"));
				member.setMember_detail_photo(rs.getString("member_detail_photo"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return member;
	}
	
	//ȸ������
	public void insertMember(MemberVO member) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;			
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		String sql = null;
		int num = 0; //������ ��ȣ ����
	
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			//ȸ�� ��ȣ ����
			sql = "SELECT member_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
			
			//ȸ�� ���� ����
			sql = "INSERT INTO member (member_num, member_id) VALUES (?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, num);
			pstmt2.setString(2, member.getMember_id());
			pstmt2.executeUpdate();
			
			//ȸ�� ���� ����2
			sql = "INSERT INTO member_detail (member_detail_num, member_detail_pw, member_detail_name, member_detail_phone) VALUES (?,?,?,?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, num);
			pstmt3.setString(2, member.getMember_detail_pw());
			pstmt3.setString(3, member.getMember_detail_name());
			pstmt3.setString(4, member.getMember_detail_phone());
			pstmt3.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
		}
	}
	
	//ȸ�� �� ����
	public MemberVO getMember(int member_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM member m JOIN member_detail d ON m.member_num=d.member_detail_num WHERE m.member_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setMember_num(rs.getInt("member_num"));
				member.setMember_id(rs.getString("member_id"));
				member.setMember_grade(rs.getInt("member_grade"));
				member.setMember_detail_pw(rs.getString("member_detail_pw"));
				member.setMember_detail_name(rs.getString("member_detail_name"));
				member.setMember_detail_phone(rs.getString("member_detail_phone"));
				member.setMember_detail_photo(rs.getString("member_detail_photo"));
				member.setMember_detail_reg_date(rs.getDate("member_detail_reg_date"));//������
				member.setMember_detail_new_date(rs.getDate("member_detail_new_date"));//������
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return member;
	}
	
	//������ ���� ����
	public void updateMyPhoto(String photo, int member_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE member_detail SET member_detail_photo=? WHERE member_detail_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, photo);
			pstmt.setInt(2, member_num);
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	
	//ȸ�� ���� ����
	public MemberVO updateMember(MemberVO member) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE member_detail SET member_detail_phone=?, member_detail_new_date=SYSDATE WHERE member_detail_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMember_detail_phone());
			pstmt.setInt(2, member.getMember_num());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return null;
	}
}











