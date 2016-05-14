/**   
* @Title: ObjectCopier.java 
* @Package nari.mip.backstage.util.objectcopier 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
* @date 2014-9-24 下午4:09:13 
* @version V1.0   
*/
package com.cht.emm.util.objectcopier;

import java.util.List;
import java.util.Set;

/** 
 * @ClassName: ObjectCopier 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn 
 * @date 2014-9-24 下午4:09:13 
 *  
 */
public interface ObjectCopier<O,V   ,VO> {

	  V copy(O o);
	V singleCopy(O o);
	List<V> copy(Set<VO> vos);
	List<V> copy(List<O> os);
}
