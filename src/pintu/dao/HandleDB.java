package pintu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pintu.entity.TimeOrder;

public class HandleDB {
	
	private Connection conn = null;
	private PreparedStatement stat = null;
	private ResultSet rs = null;
	
	public ArrayList<TimeOrder> selectInfo() {
		ArrayList<TimeOrder> al = new ArrayList<TimeOrder>();
		String sql = "select * from timeorder order by [TIME] asc";
				conn = DBUtil.getConn();
		try {
			stat = conn.prepareStatement(sql);
			rs = stat.executeQuery();
			int cnt = 0;
			while (rs.next() && cnt++ < 5) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				int time = rs.getInt(3);
				TimeOrder to = new TimeOrder(id, name, time);
				al.add(to);
				System.out.println(id + " " + name + " " + time);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
	}

	public void insertInfo(String name, int time) {
		conn = DBUtil.getConn();
		String sql = "select count(*) from timeorder where [time]<=" + time;
		try {
			stat = conn.prepareStatement(sql);
			rs = stat.executeQuery();
			if (rs.next()) {
				int n = rs.getInt(1);
				if (n <= 4) {
					sql = "insert into timeorder(NAME, TIME) values('" + name + "'," + time + ")";
					stat = conn.prepareStatement(sql);
					stat.executeUpdate();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sql);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//	HandleDB hdb = new HandleDB();
		//	hdb.insertInfo("ww", 998);
	}

}
