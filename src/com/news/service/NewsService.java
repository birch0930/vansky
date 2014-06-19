package com.news.service;

import java.io.File;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import com.news.model.NewsBean;
import com.tool.db.DB;


public class NewsService {
	PreparedStatement pstmt = null;
	Connection conn = null;
	ResultSet rs = null;

	public void saveNews(NewsBean n) {
		int id = 0;
		if (this.isNewsExist(n.getTitle()))  
			return;
		try {
			if (!n.equals(null)) {
				conn = DB.getConnection();
				pstmt = conn
						.prepareStatement("insert into news values(?,?,?,?,?,?,?,?);");
				pstmt.setString(1, null);
				pstmt.setString(2, n.getTitle());
				pstmt.setString(3, n.getContent());
				pstmt.setTimestamp(4, new Timestamp(n.getSourceTime().getTime()));   //新闻来源时间	
				pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));//新闻采集时间
				pstmt.setString(6, n.getSourceWebsite());
				pstmt.setString(7, n.getSourceUrl());
				pstmt.setInt(8, n.getStatus());
				pstmt.execute();
				
				
				//save image set
				if(n.getImgList().size() != 0){
				rs = pstmt.executeQuery("select * from news");
				rs.last();
				id  = rs.getInt(1);
				saveImgSet(id,n.getImgList());
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(pstmt);
			DB.close(conn);
		}
		
	}
	
	public void saveImgSet(final int id, List<String> list){
		try {
			conn = DB.getConnection();
			pstmt = conn.prepareStatement("insert into news_img values(?,?);");
			
			for(String imgPath : list){	
				pstmt.setInt(1, id);
				pstmt.setString(2, imgPath);
				pstmt.execute();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(pstmt);
			DB.close(conn);
		}
	}

	public boolean isNewsExist(String title) {
		boolean isExist = false;
		try {
			conn = DB.getConnection();

			pstmt = conn
					.prepareStatement("select * from news where title like ?");
			pstmt.setString(1, "%" + title + "%");
			rs = pstmt.executeQuery();
			if (rs.next())
				isExist = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			DB.close(rs);
			DB.close(pstmt);
			DB.close(conn);
		}
		return isExist;
	}

	public List<NewsBean> getNewsSet(final int pageNo, final int pageSize) {

		List<NewsBean> list = new ArrayList<NewsBean>();

		String sql = "select * from news   order by news_id desc limit ?, ? ";
		try {
			conn = DB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (pageNo - 1) * pageSize);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(initNews(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			DB.close(rs);
			DB.close(pstmt);
			DB.close(conn);
		}
		return list;
	}

	public NewsBean readNewsById(final int id) {
		NewsBean news = new NewsBean();

		String sql = "select * from news where news_id = " + id;

		try {
			conn = DB.getConnection();
			rs = conn.createStatement().executeQuery(sql);
			if (rs.next()) {

				news = initNews(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(pstmt);
			DB.close(conn);
		}
		return news;
	}

	public List<NewsBean> searchNewsBykeyWord(final String key,final int pageNo, final int pageSize) {

		List<NewsBean> list = new ArrayList<NewsBean>();
		String keyWord = key ;
		keyWord = keyWord.trim();

		String sql = "select * from news where title like ?  or content like ? order by news_id desc  limit ?, ?";

		try {
			conn = DB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyWord + "%");
			pstmt.setString(2, "%" + keyWord + "%");
			pstmt.setInt(3, (pageNo - 1) * pageSize);
			pstmt.setInt(4, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(initNews(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			DB.close(rs);
			DB.close(pstmt);
			DB.close(conn);
		}
		return list;
	}
	public int getTotalSerachNews(final String key) {
		int total = 0 ;
		String keyWord = key ;
		String sql = "select * from news where title like ?  or content like ? ";
		try {
			conn = DB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyWord + "%");
			pstmt.setString(2, "%" + keyWord + "%");
			rs = pstmt.executeQuery();
			rs.last();
			total = rs.getRow();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			DB.close(rs);
			DB.close(pstmt);
			DB.close(conn);
		}
		return total;
	}
	
	
	public int getTotalNewsNum() {
		int total = 0 ;
		String sql = "select * from news ";
		try {
			conn = DB.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.last();
			total = rs.getRow();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			DB.close(rs);
			DB.close(pstmt);
			DB.close(conn);
		}
		return total;
	}

	public void deleteNewsById(final int id) {
		deleteImgSet(id);
		String sql = "delete from news where news_id = ? ";
		try {
			conn = DB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(pstmt);
			DB.close(conn);
		}

	}
	
	public void deleteImgSet(final int id) {
		
		try {
			conn = DB.getConnection();
			pstmt = conn.prepareStatement("select * from news_img where news_id = ? ");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getString("imgPath") != null)
				deleteFile(rs.getString("imgPath"));
				else return;
			}
				
			pstmt = conn.prepareStatement("delete from news_img where news_id = ? ");
			pstmt.setInt(1, id);
			pstmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(pstmt);
			DB.close(conn);
		}

	}

	public void updateNewsById(final int id, NewsBean data) {

		String sql = "update news set title=? , content=?  where news_id = ?";
		try {
			conn = DB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getTitle());
			pstmt.setString(2, data.getContent());
			pstmt.setInt(3, id);
			pstmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(pstmt);
			DB.close(conn);
		}

	}
	
	
	public void newsRelease(final int id){
	
		String sql = "update news set status = ? where news_id = ?";
		try {
			conn = DB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 2);      // 0 is original 1 is modified 2 is released
			pstmt.setInt(2, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public void newsRevoke(final int id){
		
		String sql = "update news set status = ? where news_id = ?";
		try {
			conn = DB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);      // 0 is original 1 is Revoke 2 is released
			pstmt.setInt(2, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String sPath) {
       boolean flag = false;
       File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        
        return flag;
    }
    
    
    public NewsBean initNews(ResultSet rs){
    	NewsBean news = new NewsBean();
    	try {
    	news.setNewsId(rs.getInt("news_id"));
		news.setTitle(rs.getString("title"));
		news.setContent(rs.getString("content"));
		news.setSourceUrl(rs.getString("sourceUrl"));
		news.setSourceTime(rs.getDate("sourcedate"));
		news.setCollectTime(rs.getDate("collectdate"));
		news.setSourceWebsite(rs.getString("news_from"));
		news.setStatus(rs.getInt("status"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return news;
	}



}
