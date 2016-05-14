/**   
* @Title: DeviceCopier.java 
* @Package nari.mip.backstage.util.objectcopier 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
* @date 2014-9-24 下午4:08:05 
* @version V1.0   
*/
package com.cht.emm.util.objectcopier;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cht.emm.model.Device;
import com.cht.emm.model.id.UserDevice;
import com.cht.emm.vo.DeviceVO;


/** 
 * @ClassName: DeviceCopier 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn 
 * @date 2014-9-24 下午4:08:05 
 *  
 */
public class DeviceCopier {

	public static DeviceVO copy(Device  device){
		DeviceVO copy =  singleCopy(device);
//		DeviceDetail detail = device.getDetail();
		 
		
		
		
		return copy; 
	}
	
	public static DeviceVO singleCopy(Device  device){
		
		DeviceVO copy = new DeviceVO();
		copy.setId(device.getId());
		copy.setName(device.getName());
		copy.setOs( device.getOs());
		switch (device.getOs()) {
		case 0:
			copy.setOs_str("Android");
			break;
		case 1:
			copy.setOs_str("IOS");
			break;
		case 2:
			copy.setOs_str("windows");
			break;
		default:
			break;
		}
		copy.setStatus(device.getStatus()) ;
		switch (device.getStatus()) {
		case 0:
			copy.setStatus_str("注册");
			break;
		case 2:  
				copy.setStatus_str("驳回");
				break;
		case 1:
				copy.setStatus_str("激活");
				break;
		case 3:
				copy.setStatus_str("注销");
				break;
		default:
			break;
		}
		copy.setType(device.getType()) ;
		switch (device.getType()) {
		case 0:
			copy.setType_str("个人");
			break;
		case 1:
			copy.setType_str("企业");
			break;
		case 2:
			copy.setType_str("共用");
			break;
		default:
			break;
		}
		return copy;
	}
	
	public static List<DeviceVO> copy(List<Device> devices){
		List<DeviceVO> copies = new ArrayList<DeviceVO>();
		return copies;
	}
	
	public static List<DeviceVO> copy(Set<UserDevice> userDevices){
		if(userDevices!=null){
			
			List<DeviceVO> copies = new ArrayList<DeviceVO>();
			for (UserDevice device : userDevices) {
				DeviceVO copy = copy(device.getDevice());
				copies.add(copy);
			}
			return copies;
			
		}
		return null;
	}
}
