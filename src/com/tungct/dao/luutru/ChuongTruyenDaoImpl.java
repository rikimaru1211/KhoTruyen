package com.tungct.dao.luutru;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteConcern;
import com.tungct.domain.luutru.ChuongTruyen;
import com.tungct.utils.HelpFunction;

public class ChuongTruyenDaoImpl implements ChuongTruyenDao{
	
	private MongoTemplate mongoTemplate;
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public String CollectionName() {
		return "ChuongTruyen";
	}
	
	@Override
	public void insert(ChuongTruyen vChuongTruyen) throws Exception {
		this.mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		this.mongoTemplate.insert(vChuongTruyen, CollectionName());
	}
	@Override
	public void update(ChuongTruyen vChuongTruyen) throws Exception {
		Query query = new Query(Criteria.where(ChuongTruyen.ID).is(new ObjectId(vChuongTruyen.get_id())));
		Update update = new Update();
		update.set(ChuongTruyen.MA_TRUYEN, vChuongTruyen.getMatruyen());
		update.set(ChuongTruyen.NOI_DUNG, vChuongTruyen.getNoidung());
		update.set(ChuongTruyen.SO_THU_TU, vChuongTruyen.getStt());
		update.set(ChuongTruyen.TIEU_DE, vChuongTruyen.getTieude());
		update.set(ChuongTruyen.URL_GOC, vChuongTruyen.getUrlgoc());
		
		this.mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		this.mongoTemplate.updateFirst(query, update, CollectionName());
	}
	@Override
	public void delete(String id) throws Exception {
		Query query = new Query(Criteria.where(ChuongTruyen.ID).is(new ObjectId(id)));
		this.mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		this.mongoTemplate.remove(query, CollectionName());
	}
	
	@Override
	public ChuongTruyen SelectByID(String id) throws Exception {
		Query query = new Query(Criteria.where(ChuongTruyen.ID).is(new ObjectId(id)));
		ChuongTruyen vChuongTruyen = null;
		try {
			vChuongTruyen = this.mongoTemplate.findOne(query, ChuongTruyen.class, CollectionName());
		} catch(EmptyResultDataAccessException ex) {
			vChuongTruyen = null;
		}
		return vChuongTruyen;
	}
	@Override
	public Long Count() throws Exception {
		Query query = new Query();
		return this.mongoTemplate.count(query, CollectionName());
	}
	
	@Override
	public List<ChuongTruyen> SelectByMaTruyenVaTuKhoa(String sMaTruyen, String sTuKhoa, boolean bSort, int nFirstRow, int nPageSize) throws Exception {
		Criteria criteria = new Criteria();
		if(!HelpFunction.isEmpty(sMaTruyen)){
			criteria.and(ChuongTruyen.MA_TRUYEN).is(sMaTruyen);
		}
		if(!HelpFunction.isEmpty(sTuKhoa)){
			criteria.and(ChuongTruyen.TIEU_DE).regex(sTuKhoa, "i");
		}
		Query query = new Query(criteria);
		if(bSort){
			query.with(new Sort(Sort.Direction.ASC, ChuongTruyen.SO_THU_TU));
		}
		query.skip(nFirstRow);
		query.limit(nPageSize); 
		List<ChuongTruyen> list = null;
		try	
		{
			list = this.mongoTemplate.find(query, ChuongTruyen.class, CollectionName());
		} catch(EmptyResultDataAccessException ex) {
			list = null;
		}
		return list;
	}
	@Override
	public Long CountByMaTruyenVaTuKhoa(String sMaTruyen, String sTuKhoa) throws Exception {
		Criteria criteria = new Criteria();
		if(!HelpFunction.isEmpty(sMaTruyen)){
			criteria.and(ChuongTruyen.MA_TRUYEN).is(sMaTruyen);
		}
		if(!HelpFunction.isEmpty(sTuKhoa)){
			criteria.and(ChuongTruyen.TIEU_DE).regex(sTuKhoa, "i");
		}
		Query query = new Query(criteria);
		
		return this.mongoTemplate.count(query, CollectionName());
	}
}
