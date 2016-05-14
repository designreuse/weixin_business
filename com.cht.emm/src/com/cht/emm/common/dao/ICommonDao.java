package com.cht.emm.common.dao;



import java.io.Serializable;
import java.util.List;

import com.cht.emm.common.model.AbstractModel;


/**
 * 
* @ClassName: ICommonDao 
* @Description: 基础服务接口
* @author 张凯  zhangkai3@sgepri.sgcc.com.cn 
* @date 2014-9-3 下午4:56:20 
*
 */
public interface ICommonDao {
    
	
	/**
	* @Title: save 
	* @Description: 保存Entiry 
	* @param @param model
	* @param @return    设定 Entity
	* @return T    返回类型  返回保存后的Entity
	* @throws
	 */
    public <T extends AbstractModel> T save(T model);
    
    /**
     * 
    * @Title: saveOrUpdate 
    * @Description:   保存或更新 Entity 
    * @param  model   设定对象
    * @return void    返回类型 
    * @throws
     */
    public <T extends AbstractModel> void saveOrUpdate(T model);
    
    
    /**
    * @Title: update 
    * @Description:   更新 Entity
    * @param  model   设定对象 
    * @return void    返回类型 
    * @throws
     */
    public <T extends AbstractModel> void update(T model);
    
    /**
    * @Title: merge 
    * @Description: 	merge Entity 
    * @param  model    	设定对象 
    * @return void    	返回类型 
    * @throws
     */
    public <T extends AbstractModel> void merge(T model);
    
    /**
     * 
    * @Title: delete 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param entityClass
    * @param @param id    设定文件 
    * @return void    返回类型 
    * @throws
     */

    public <T extends AbstractModel, PK extends Serializable> void delete(Class<T> entityClass, PK id);

    public <T extends AbstractModel> void deleteObject(T model);

    public <T extends AbstractModel, PK extends Serializable> T get(Class<T> entityClass, PK id);
    
    public <T extends AbstractModel> int countAll(Class<T> entityClass);
    
    public <T extends AbstractModel> List<T> listAll(Class<T> entityClass);
    
    public <T extends AbstractModel> List<T> listAll(Class<T> entityClass, int pn);
    
    public <T extends AbstractModel> List<T> listAll(Class<T> entityClass, int pn, int pageSize);
    

}
