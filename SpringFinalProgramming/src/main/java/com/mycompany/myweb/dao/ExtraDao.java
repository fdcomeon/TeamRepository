package com.mycompany.myweb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mycompany.myweb.dto.Extra;



public class ExtraDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int insert(Extra extra){
		
		String sql = "insert into extra(xid, xname, xprice) "
				+ "values(seq_extra_xid.nextval, ?, ?)";
		
		int row = jdbcTemplate.update(
				sql,
				extra.getXid(),
				extra.getXname(),
				extra.getXprice()
				);
		return row;
	}
	
	public int update(Extra extra){
		String sql = "update extra set xname=?, xprice=? where xid=?";
		int row = jdbcTemplate.update(
				sql,
				extra.getXname(),
				extra.getXprice(),
				extra.getXid()
				);
		return row;
	}
	
	public int delete(int xid){
		String sql = "delete from extra where xid=?";
		
		int row = jdbcTemplate.update(sql,xid);
		return row;
	}
	
	public Extra selectByXid(int xid){
		String sql="select xid, xname, xprice from extra where xid=?";
		List<Extra> list = jdbcTemplate.query(sql, new Object[]{xid}, new RowMapper<Extra>(){

			@Override
			public Extra mapRow(ResultSet rs, int row) throws SQLException {
				// TODO Auto-generated method stub
				Extra extra = new Extra();
				extra.setXid(rs.getInt("xid"));
				extra.setXname(rs.getString("xname"));
				extra.setXprice(rs.getInt("xprice"));
				return extra;
			}
			
		});
		
		return (list.size() != 0)? list.get(0):null;
	}
}
