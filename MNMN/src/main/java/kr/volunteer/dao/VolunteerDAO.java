package kr.volunteer.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kr.volunteer.vo.VolunteerVO;
import kr.util.DBUtil;

public class VolunteerDAO {
private static VolunteerDAO instance = new VolunteerDAO();
	
	public static VolunteerDAO getInstance() {
		return instance;
	}
	private VolunteerDAO() {}
	
	//context.xml���� ���� ������ �о�鿩 Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
	private Connection getConnection()throws Exception{
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/xe");
		return ds.getConnection();
	}
	//�ڿ�����
	private void executeClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		if(rs!=null)try {rs.close();}catch(SQLException e) {}
		if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
		if(conn!=null)try {conn.close();}catch(SQLException e) {}
	}
	
	public void insertVolunteer(VolunteerVO vol)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null; 
		
		try{
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			
			//�����ȣ(vol_num) ����
			sql = "INSERT INTO volunteer (vol_num, vol_member_num, vol_date, vol_time, vol_reg_date)"
					+ " VALUES (volunteer_seq.nextval, ?, ?, ?, SYSDATE)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, vol.getVol_m_num());
			pstmt.setDate(2, vol.getVol_date());
			pstmt.setInt(3, vol.getVol_time());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			executeClose(null, pstmt, conn);
		}
	}
	
	public void deleteVolunteer(VolunteerVO vol)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null; 
		
		try{
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			
			//�����ȣ(vol_num) ����
			sql = "DELETE FROM volunteer WHERE to_char(vol_date, 'YY/MM/DD') = ? and vol_time = ? and vol_member_num = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
			
			pstmt.setString(1, sdf.format(vol.getVol_date()));
			pstmt.setInt(2, vol.getVol_time());
			pstmt.setInt(3, vol.getVol_m_num());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			executeClose(null, pstmt, conn);
		}
	}
	
	public int checkDateVolunteer(String date)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		int volCount = 0;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			sql = "SELECT count(*) as count FROM volunteer WHERE to_char(vol_date, 'YY/MM/DD') = ?";
			
			if(date.length() == 7 && (date.charAt(3) == '1' || date.charAt(3) == '2')) {
				date = date.substring(0, 6) + "0" + date.substring(6);
			}else if(date.length() == 7 && date.charAt(3) != '0')
				date = date.substring(0, 3) + "0" + date.substring(3);
			if(date.length() == 6)
				date = date.substring(0, 3) + "0" + date.substring(3, 5) + "0" + date.substring(5);
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			
			//SQL���� �����ؼ� ������� ResultSet�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				volCount = rs.getInt("count");
			}
			System.out.println("���� ��û�� ���� �� : " + volCount);
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return volCount;
	}
	
	public int checkDateFull(String date, int time)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		int volCount = 0;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			sql = "SELECT count(*) as count FROM volunteer WHERE to_char(vol_date, 'YY/MM/DD') = ? and vol_time = ?";
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.setInt(2, time);
			
			//SQL���� �����ؼ� ������� ResultSet�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				volCount = rs.getInt("count");
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return volCount;
	}
	
	public int checkAlready(int user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		int volCount = 0;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			sql = "SELECT count(*) as count FROM volunteer WHERE vol_member_num = ? and vol_checked = 0";
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_num);
			
			//SQL���� �����ؼ� ������� ResultSet�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				volCount = rs.getInt("count");
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return volCount;
	}
	
	public int checkDateRequest(String date, int time)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		int Requested = 0;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			sql = "SELECT count(*) as count FROM volunteer WHERE to_char(vol_date, 'YY/MM/DD') = ? and vol_time = ?";
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.setInt(2, time);
			
			//SQL���� �����ؼ� ������� ResultSet�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Requested = rs.getInt("count");
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return Requested;
	}
	
	//�ű� ���� ��û ����
	public int getVolunteerCount() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
			
		try {
			conn = DBUtil.getConnection();
				
			sql = "SELECT COUNT(*) FROM volunteer v JOIN member m ON v.vol_member_num = m.member_num WHERE vol_checked=0";
				
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
		
	//�ű� ���� ��û ���
	public List<VolunteerVO> getListVolunteer(int start, int end) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<VolunteerVO> list = null;
		String sql = null;
			
		try {
			conn = DBUtil.getConnection();
				
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
				+ "(SELECT * FROM volunteer v JOIN member m ON v.vol_member_num = m.member_num "
				+ "WHERE vol_checked=0 ORDER BY v.vol_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
				
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
				
			rs = pstmt.executeQuery();
			list = new ArrayList<VolunteerVO>();
			while(rs.next()) {
				VolunteerVO volunteer = new VolunteerVO();
				volunteer.setVol_num(rs.getInt("vol_num"));
				volunteer.setVol_m_num(rs.getInt("vol_member_num"));
				volunteer.setVol_m_id(rs.getString("member_id"));
				volunteer.setVol_reg_date(rs.getDate("vol_reg_date"));
				volunteer.setVol_date(rs.getDate("vol_date"));
				volunteer.setVol_time(rs.getInt("vol_time"));
				volunteer.setVol_checked(rs.getInt("vol_checked"));

				list.add(volunteer);
			}
				
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
		
	//����Ȱ�� ����
	public boolean confirmVolunteer(int vol_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
			
		try {
			conn = DBUtil.getConnection();
				
			sql = "UPDATE volunteer SET vol_checked=? WHERE vol_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, vol_num);
				
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
		return true;
	}
	
	//����Ȱ�� �ҽ���
	public boolean dismissVolunteer(int vol_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
			
		try {
			conn = DBUtil.getConnection();
				
			sql = "DELETE volunteer WHERE vol_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vol_num);
				
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
		return true;
	}
}
