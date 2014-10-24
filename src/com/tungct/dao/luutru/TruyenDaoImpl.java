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
import com.tungct.domain.luutru.Truyen;
import com.tungct.utils.HelpFunction;

public class TruyenDaoImpl implements TruyenDao{
	
	private MongoTemplate mongoTemplate;
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public String CollectionName() {
		return "Truyen";
	}
	
	@Override
	public void insert(Truyen vTruyen) throws Exception {
		this.mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		this.mongoTemplate.insert(vTruyen, CollectionName());
	}
	@Override
	public void update(Truyen vTruyen) throws Exception {
		Query query = new Query(Criteria.where(Truyen.ID).is(new ObjectId(vTruyen.get_id())));
		Update update = new Update();
		update.set(Truyen.MA_NGUON, vTruyen.getManguon());
		update.set(Truyen.MA_TRUYEN, vTruyen.getMatruyen());
		update.set(Truyen.SO_CHUONG, vTruyen.getSochuong());
		update.set(Truyen.TEN, vTruyen.getTen());
		update.set(Truyen.TEN_HIEN_THI, vTruyen.getTenhienthi());
		update.set(Truyen.URL_GOC, vTruyen.getUrlgoc());
		update.set(Truyen.THOI_DIEM_CAP_NHAT, vTruyen.getTdcapnhat());
		
		this.mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		this.mongoTemplate.updateFirst(query, update, CollectionName());
	}
	@Override
	public void delete(String id) throws Exception {
		Query query = new Query(Criteria.where(Truyen.ID).is(new ObjectId(id)));
		this.mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		this.mongoTemplate.remove(query, CollectionName());
	}
	
	@Override
	public void DeleteByMaTruyen(String sMaTruyen) throws Exception {
		Query query = new Query(Criteria.where(Truyen.MA_TRUYEN).is(sMaTruyen));
		this.mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		this.mongoTemplate.remove(query, CollectionName());
	}
	
	@Override
	public Truyen SelectByID(String id) throws Exception {
		Query query = new Query(Criteria.where(Truyen.ID).is(new ObjectId(id)));
		Truyen vTruyen = null;
		try {
			vTruyen = this.mongoTemplate.findOne(query, Truyen.class, CollectionName());
		} catch(EmptyResultDataAccessException ex) {
			vTruyen = null;
		}
		return vTruyen;
	}
	@Override
	public Truyen SelectByMa(String sMaTruyen) throws Exception {
		Query query = new Query(Criteria.where(Truyen.MA_TRUYEN).is(sMaTruyen));
		Truyen vTruyen = null;
		try {
			vTruyen = this.mongoTemplate.findOne(query, Truyen.class, CollectionName());
		} catch(EmptyResultDataAccessException ex) {
			vTruyen = null;
		}
		return vTruyen;
	}
	@Override
	public Long Count() throws Exception {
		Query query = new Query();
		return this.mongoTemplate.count(query, CollectionName());
	}
	
	@Override
	public List<Truyen> SelectByNguonVaTuKhoa(String sNguon, String sTuKhoa, int nFirstRow, int nPageSize) throws Exception {
		Criteria criteria = new Criteria();
		if(!HelpFunction.isEmpty(sNguon)){
			criteria.and(Truyen.MA_NGUON).is(sNguon);
		}
		if(!HelpFunction.isEmpty(sTuKhoa)){
			criteria.and(Truyen.CHUOI_TIM_KIEM).regex(sTuKhoa, "i");
		}
		Query query = new Query(criteria);
		query.with(new Sort(Sort.Direction.DESC, Truyen.TEN));
		query.skip(nFirstRow);
		query.limit(nPageSize); 
		List<Truyen> list = null;
		try	
		{
			list = this.mongoTemplate.find(query, Truyen.class, CollectionName());
		} catch(EmptyResultDataAccessException ex) {
			list = null;
		}
		return list;
	}
	@Override
	public Long CountByNguonVaTuKhoa(String sNguon, String sTuKhoa) throws Exception {
		Criteria criteria = new Criteria();
		if(!HelpFunction.isEmpty(sNguon)){
			criteria.and(Truyen.MA_NGUON).is(sNguon);
		}
		if(!HelpFunction.isEmpty(sTuKhoa)){
			criteria.and(Truyen.CHUOI_TIM_KIEM).regex(sTuKhoa, "i");
		}
		Query query = new Query(criteria);
		
		return this.mongoTemplate.count(query, CollectionName());
	}

}
