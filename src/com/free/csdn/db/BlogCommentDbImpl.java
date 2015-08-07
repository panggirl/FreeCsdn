package com.free.csdn.db;

import java.util.List;

import com.free.csdn.bean.Comment;
import com.free.csdn.util.FileUtil;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import android.content.Context;

/**
 * 博客评论数据库-实现
 * 
 * @author tangqi
 * @data 2015年8月7日下午11:53:19
 */

public class BlogCommentDbImpl implements BlogCommentDb {

	private DbUtils db;

	public BlogCommentDbImpl(Context context, String filename) {
		// TODO Auto-generated method stub
		db = DbUtils.create(context, FileUtil.getExternalCacheDir(context)
				+ "/CommentList", filename + "_comment");
	}

	public void saveCommentList(List<Comment> list) {
		try {
			for (int i = 0; i < list.size(); i++) {
				Comment commentItem = list.get(i);
				Comment findItem = db.findFirst(Selector.from(Comment.class)
						.where("commentId", "=", commentItem.getCommentId()));
				if (findItem != null) {
					db.update(
							commentItem,
							WhereBuilder.b("commentId", "=",
									commentItem.getCommentId()));
				} else {
					db.save(commentItem);
				}
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Comment> getCommentList(int page) {
		try {
			List<Comment> list = db.findAll(Selector.from(Comment.class).limit(
					20 * page));
			return list;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
