package com.mycompany.myweb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.mycompany.myweb.dto.Menu;




@Component
public class MenuDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	   
	public int insert(Menu menu){
		String sql = "insert into menu(mid, mgroup, mname, hot_ice, mcontents, msavedfile, mmimetype, sid, xid) values(sequence_mid, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int row = jdbcTemplate.update(
				sql,
				menu.getMgroup(),
				menu.getMname(), 
				menu.getHot_ice(),
				menu.getMcontents(),
				menu.getMsavedfile(),
				menu.getMmimetype(),
				menu.getSid(),
				menu.getXid()
		);
		return row;
	}
	
	public int update(Menu menu){
		String sql = "update menu set mgroup=?, mname=?, hot_ice=?, msavedfile=?, sid=?, xid=? where mid=?";
		int row = jdbcTemplate.update(
				sql,
				menu.getMgroup(),
				menu.getMname(),
				menu.getHot_ice(),
				menu.getMcontents(),
				menu.getMsavedfile(),
				menu.getMmimetype(),
				menu.getSid(),
				menu.getXid(),
				menu.getMid()
		);
		return row;
	}
	
	public int delete(int mid){
		String sql = "delete from menu where mid=?";
		int row = jdbcTemplate.update(sql, mid);
		return row;
	}
	
	public Menu selectByMid(int mid){
		String sql = "select mid, mgroup, mname, hot_ice, mcontents, msavedfile, mmimetype, sid, xid from menu where mid=?";
		List<Menu> list = jdbcTemplate.query(sql, new Object[]{mid}, new RowMapper<Menu>(){
			
			@Override
			public Menu mapRow(ResultSet rs, int row) throws SQLException {
				Menu menu = new Menu();
				menu.setMid(rs.getInt("mid"));
				menu.setMgroup(rs.getString("mgroup"));
				menu.setMname(rs.getString("mname"));
				menu.setHot_ice(rs.getString("hot_ice"));
				menu.setMcontents(rs.getString("mcontents"));
				menu.setMsavedfile(rs.getString("msavedfile"));
				menu.setMmimetype(rs.getString("mmimetype"));
				menu.setSid(rs.getInt("sid"));
				menu.setXid(rs.getInt("xid"));
				return menu;
			}
		});
		return (list.size() != 0)? list.get(0) : null;
	}
	
	public List<Menu> selectByPage(int pageNo, int rowsPerPage){
		String sql="";
		sql += "select rn, mid, mgroup, mname, hot_ice, mcontents, msavedfile, mmimetype, sid, xid ";
		sql += "from ( ";
		sql += "select rownum as rn, mid, mgroup, mname, hot_ice, mcontents, msavedfile, mmimetype, sid, xid ";
		sql += "from ( select mid, mgroup, mname, hot_ice, mcontents, msavedfile, mmimetype, sid, xid from menu order by mid desc) ";
		sql += ") ";
		sql += "where rn>=? ";
		List<Menu> list = jdbcTemplate.query(
				sql, 
				new Object[]{(pageNo*rowsPerPage), ((pageNo-1)*rowsPerPage+1)},
				new RowMapper<Menu>(){
					@Override
					public Menu mapRow(ResultSet rs, int row) throws SQLException {
						Menu menu = new Menu();
						menu.setMid(rs.getInt("mid"));
						menu.setMgroup(rs.getString("mgroup"));
						menu.setMname(rs.getString("mname"));
						menu.setHot_ice(rs.getString("hot_ice"));
						menu.setMcontents(rs.getString("mcontents"));
						menu.setMsavedfile(rs.getString("msavedfile"));
						menu.setMmimetype(rs.getString("mmimetype"));
						menu.setSid(rs.getInt("sid"));
						menu.setXid(rs.getInt("xid"));
						return menu;
					}
				}
		);
		return list;
	}
	
	
	public int count(){
		String sql = "select count(*) from menu";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		return count;
	}
	
}