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
import com.tungct.domain.luutru.NguonTruyen;
import com.tungct.utils.HelpFunction;

public class NguonTruyenDaoImpl implements NguonTruyenDao{

	private MongoTemplate mongoTemplate;
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public String CollectionName() {
		return "NguonTruyen";
	}
	
	@Override
	public void insert(NguonTruyen vNguonTruyen) throws Exception {
		this.mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		this.mongoTemplate.insert(vNguonTruyen, CollectionName());
	}
	@Override
	public void update(NguonTruyen vNguonTruyen) throws Exception {
		Query query = new Query(Criteria.where(NguonTruyen.ID).is(new ObjectId(vNguonTruyen.get_id())));
		Update update = new Update();
		update.set(NguonTruyen.MA_NGUON, vNguonTruyen.get_id());
		update.set(NguonTruyen.URL_GOC, vNguonTruyen.getUrlgoc());
		update.set(NguonTruyen.URL_INDEX, vNguonTruyen.getUrlindex());
		update.set(NguonTruyen.KIEU_URL_GOC, vNguonTruyen.getKieuurlgoc());
		update.set(NguonTruyen.KIEU_INDEX, vNguonTruyen.getKieuindex());
		update.set(NguonTruyen.DIV_CHAPTER, vNguonTruyen.getDivchapter());
		update.set(NguonTruyen.DIV_PAGE, vNguonTruyen.getDivpage());
		update.set(NguonTruyen.URL_DOC, vNguonTruyen.getUrldoc());
		update.set(NguonTruyen.TIEN_TO, vNguonTruyen.getTiento());
		update.set(NguonTruyen.DIV_TITLE, vNguonTruyen.getDivtitle());
		update.set(NguonTruyen.DIV_CONTENT, vNguonTruyen.getDivcontent());
		update.set(NguonTruyen.THOI_DIEM_CAP_NHAT, vNguonTruyen.getTdcapnhat());
		
		this.mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		this.mongoTemplate.updateFirst(query, update, CollectionName());
	}
	@Override
	public void delete(String id) throws Exception {
		Query query = new Query(Criteria.where(NguonTruyen.ID).is(new ObjectId(id)));
		this.mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		this.mongoTemplate.remove(query, CollectionName());
	}
	
	@Override
	public NguonTruyen SelectByID(String id) throws Exception {
		Query query = new Query(Criteria.where(NguonTruyen.ID).is(new ObjectId(id)));
		NguonTruyen vNguonTruyen = null;
		try {
			vNguonTruyen = this.mongoTemplate.findOne(query, NguonTruyen.class, CollectionName());
		} catch(EmptyResultDataAccessException ex) {
			vNguonTruyen = null;
		}
		return vNguonTruyen;
	}
	@Override
	public NguonTruyen SelectByMa(String sMaNguon) throws Exception {
		Query query = new Query(Criteria.where(NguonTruyen.MA_NGUON).is(sMaNguon));
		NguonTruyen vNguonTruyen = null;
		try {
			vNguonTruyen = this.mongoTemplate.findOne(query, NguonTruyen.class, CollectionName());
		} catch(EmptyResultDataAccessException ex) {
			vNguonTruyen = null;
		}
		return vNguonTruyen;
	}
	@Override
	public Long Count() throws Exception {
		Query query = new Query();
		return this.mongoTemplate.count(query, CollectionName());
	}
	
	@Override
	public List<NguonTruyen> SelectByTuKhoa(String sTuKhoa, List<String> lstFields) throws Exception {
		Criteria criteria = new Criteria();
		if(!HelpFunction.isEmpty(sTuKhoa)){
			criteria.orOperator(Criteria.where(NguonTruyen.MA_NGUON).regex(sTuKhoa, "i"),
								Criteria.where(NguonTruyen.URL_INDEX).regex(sTuKhoa, "i"));
		}
		
		Query query = new Query(criteria);
		if(lstFields != null && lstFields.size() > 0){
			for (String field : lstFields) {
				query.fields().include(field);
			}
		}
		query.with(new Sort(Sort.Direction.DESC, NguonTruyen.MA_NGUON));
		
		List<NguonTruyen> list = null;
		try	
		{
			list = this.mongoTemplate.find(query, NguonTruyen.class, CollectionName());
		} catch(EmptyResultDataAccessException ex) {
			list = null;
		}
		return list;
	}
	@Override
	public List<NguonTruyen> SelectByTuKhoa(String sTuKhoa, int nFirstRow, int nPageSize) throws Exception {
		Criteria criteria = new Criteria();
		if(!HelpFunction.isEmpty(sTuKhoa)){
			criteria.orOperator(Criteria.where(NguonTruyen.MA_NGUON).regex(sTuKhoa, "i"),
								Criteria.where(NguonTruyen.URL_INDEX).regex(sTuKhoa, "i"));
		}
		Query query = new Query(criteria);
		query.with(new Sort(Sort.Direction.DESC, NguonTruyen.MA_NGUON));
		query.skip(nFirstRow);
		query.limit(nPageSize); 
		List<NguonTruyen> list = null;
		try	
		{
			list = this.mongoTemplate.find(query, NguonTruyen.class, CollectionName());
		} catch(EmptyResultDataAccessException ex) {
			list = null;
		}
		return list;
	}
	@Override
	public Long CountByTuKhoa(String sTuKhoa) throws Exception {
		Criteria criteria = new Criteria();
		if(!HelpFunction.isEmpty(sTuKhoa)){
			criteria.orOperator(Criteria.where(NguonTruyen.MA_NGUON).regex(sTuKhoa, "i"),
								Criteria.where(NguonTruyen.URL_INDEX).regex(sTuKhoa, "i"));
		}
		Query query = new Query(criteria);
		
		return this.mongoTemplate.count(query, CollectionName());
	}
}
