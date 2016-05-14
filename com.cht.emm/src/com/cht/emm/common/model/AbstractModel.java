package com.cht.emm.common.model;



import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.service.ICommonService;
import com.cht.emm.common.util.SpringContextUtil;

/**
 * 
* @ClassName: AbstractModel 
* @Description: 基础模型 
* @author 张凯  zhangkai3@sgepri.sgcc.com.cn 
* @date 2014-10-10 下午4:58:30 
 */
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public abstract class AbstractModel implements java.io.Serializable {
    
    private static final long serialVersionUID = 2035013017939483936L;


    public static final int IS_SYSTEM_TYPE_UNDELETE =2;
    public static final int IS_SYSTEM_TYPE_UNSEEN =1;
    public static final int IS_SYSTEM_TYPE_NO =0;
    
    protected boolean deletable =true ;
    
    protected Integer isSystem =IS_SYSTEM_TYPE_NO;
 
	/**
	 * @return the isSystem
	 */
    
	public abstract Integer getIsSystem();


	/**
	 * @param isSystem the isSystem to set
	 */
	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}


	/**
	 * @return   deletable
	 */
	public boolean isDeletable() {
		return deletable;
	}


	/** 
	 * @param deletable 要设置的 deletable 
	 */
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}


	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    
    public void save() {
        ICommonService commonService = SpringContextUtil.getBean("CommonService");
        commonService.save(this);
    }
    
    public void delete() {
        ICommonService commonService = SpringContextUtil.getBean("CommonService");
        commonService.deleteObject(this);
    }
    
    public void update() {
        ICommonService commonService = SpringContextUtil.getBean("CommonService");
        commonService.update(this);
    }
}
