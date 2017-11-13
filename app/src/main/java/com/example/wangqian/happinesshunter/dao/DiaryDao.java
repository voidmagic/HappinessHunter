package com.example.wangqian.happinesshunter.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wangqian.happinesshunter.db.DBOpenHelper;
import com.example.wangqian.happinesshunter.entity.Diary;

import java.util.ArrayList;
import java.util.List;


/**
 * 完成了对数据库中的数据进行增删改查操作
 */
public class DiaryDao {
   private DBOpenHelper dbOpenHelper;
   private SQLiteDatabase db;
   public DiaryDao(Context context){
	   dbOpenHelper=new DBOpenHelper(context);
   }
   
   /**
    * 保存日记
    * @param diary
    */
   public void save(Diary diary){
	   db=dbOpenHelper.getWritableDatabase();
	   ContentValues values=new ContentValues();
	   values.put("title", diary.getTitle());
	   values.put("content", diary.getContent());
	   values.put("happy",diary.getHappy());
	   values.put("createtime", diary.getCreatetime());
	   db.insert("diary", null, values);
   }
   
   /**
    * 根据id删除日记
    * @param id
    */
   public void delete(Integer id){
	   //TODO 要求2：此处插入若干行代码，实现根据id删除日记的功能。10分  
	  // db = dbOpenHelper.getWritableDatabase();// 得到的是同一个数据库实例
		//db.execSQL("delete from diary where _id=?",new Object[]{id});

	   db=dbOpenHelper.getWritableDatabase();
       db.delete("diary","_id=?",new String[]{id.toString()});

   }

    public void update(Diary diary){
    	db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("title", diary.getTitle());
		values.put("content", diary.getContent());
		values.put("happy", diary.getHappy());
		values.put("createtime", diary.getCreatetime());
		//TODO 调用SQLiteDatabase提供的update方法完成更新操作
		db.update("diary", values, "_id=?", new String[]{diary.getId().toString()});
		db.close();
    }

   /**
    * 查询所有日记
    * @return Cursor
    */
   public Cursor getAllDiaries(){
	   db=dbOpenHelper.getReadableDatabase();
	   Cursor cursor=db.query("diary", null, null, null, null, null, null);
	   return cursor;
   }

	public List<Diary> getAllDiariesData(){
		db=dbOpenHelper.getReadableDatabase();
		Cursor cursor=db.query("diary", null, null, null, null, null, null);
		List<Diary> diaryList = new ArrayList<Diary>();
		while(cursor.moveToNext()){
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String content = cursor.getString(cursor.getColumnIndex("content"));
			String time = cursor.getString(cursor.getColumnIndex("createtime"));
			Integer happy  = cursor.getInt(cursor.getColumnIndex("happy"));
			Diary diary = new Diary(id,title, content,time,happy);
			diaryList.add(diary);
		}
		return diaryList;
	}


   
   /**
    * 根据id查找日记
    * @param id
    * @return
    */
   public Diary getDiaryById(Integer id){
	   Diary diary=null;
	   db=dbOpenHelper.getReadableDatabase();
	   Cursor cursor=db.query("diary", null, "_id=?", new String[]{id.toString()},null, null, null);
	   if(cursor!=null){
		   if(cursor.moveToFirst()){
			   String title=cursor.getString(cursor.getColumnIndex("title"));
			   String content=cursor.getString(cursor.getColumnIndex("content"));
			   String createtime=cursor.getString(cursor.getColumnIndex("createtime"));
			   Integer happy  = cursor.getInt(cursor.getColumnIndex("happy"));
			   diary = new Diary(id,title, content,createtime,happy);
			   diary.setId(id);
		   }
	   }
	   return diary;
   }
   
   /**
    * 计算日记数量
    * @return
    */
   public long count(){
	   long count=0;
	   db=dbOpenHelper.getReadableDatabase();
	   Cursor cursor=db.query("diary", new String[]{"count(*)"}, null, null, null, null, null);
	   if(cursor.moveToFirst()){
		   count=cursor.getLong(0);
	   }
	   return count;
   }
   
}
