package com.cht.emm.model.id;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModel;
import com.cht.emm.model.Group;
import com.cht.emm.model.User;



/**
 * UserGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="mip_sys_user_group"
)
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class UserGroup  extends AbstractModel {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 3343251985910755670L;
	 @Id 
	 private String id;
     private User user;
     private Group group;


    // Constructors

    /** default constructor */
    public UserGroup() {
    }

	/** minimal constructor */
    public UserGroup(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public UserGroup(String id, User user, Group group) {
        this.id = id;
        this.user = user;
        this.group = group;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="id",length=36)

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="user_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="group_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
    public Group getGroup() {
        return this.group;
    }
    
    public void setGroup(Group group) {
        this.group = group;
    }
   
 
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
}