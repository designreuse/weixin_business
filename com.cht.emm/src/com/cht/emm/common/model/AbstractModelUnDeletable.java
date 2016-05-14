/**   
* @Title: AbstractModelDeleable.java 
* @Package nari.mip.backstage.common.model 
* @Description: 软删除的实体类 
* @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
* @date 2014-10-10 下午4:55:23 
* @version V1.0   
*/

package com.cht.emm.common.model;




/** 
 * @ClassName: AbstractModelDeleable 
 * @Description: 软删除的实体类 
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn 
 * @date 2014-10-10 下午4:55:23 
 *  
 *  注意点：
 *  	1.对于映射关系，删除时需要按照需求自行解绑处理
 *  	2.查询时，对于已删除的实体需不需要处理，可以再另行通过标识量进行区分，默认不需要
 *  
 *  
 */

 


public abstract class AbstractModelUnDeletable extends AbstractModel{

	/**
	* <p>Title: save</p> 
	* <p>Description: </p>  
	* @see com.cht.emm.common.model.AbstractModel#save() 
	*/
	@Override
	public void save() {
		// TODO Auto-generated method stub
		if(this.getDeleted() ==null){
			this.setDeleted(0);
		}
		super.save();
	}


	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -1658454201495129291L;
	
	
	/**
	 * 
	 * <p>Title: </p> 
	 * <p>Description:需要注意，所有软删除的构造方法是用super(),以确保该标志量被设成false </p>
	 */
	public AbstractModelUnDeletable(){
		deletable =false;
	}
	
	
	protected Integer deleted =0;

	
	/**
	 * @return   deleted
	 */
	public abstract Integer getDeleted();


	/** 
	 * @param deleted 要设置的 deleted 
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}


	/**
	* <p>Title: delete</p> 
	* <p>Description:重写了delete方法，删除时只需将deleted置1 即可 </p>  
	* @see com.cht.emm.common.model.AbstractModel#delete() 
	*/
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		setDeleted(1);
		update();
	}
	


}
