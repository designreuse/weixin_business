package com.cht.emm.vo;

import java.util.List;

public class DeviceDetailVO {
	private DeviceVO device;
	private List<ApplicationDeployVO> deploys;
	private List<ConfigVO> configs;
	private List<StrategyVO> strategys;

	public DeviceVO getDevice() {
		return device;
	}

	public void setDevice(DeviceVO device) {
		this.device = device;
	}

	public List<ApplicationDeployVO> getDeploys() {
		return deploys;
	}

	public void setDeploys(List<ApplicationDeployVO> deploys) {
		this.deploys = deploys;
	}

	public List<ConfigVO> getConfigs() {
		return configs;
	}

	public void setConfigs(List<ConfigVO> configs) {
		this.configs = configs;
	}

	public List<StrategyVO> getStrategys() {
		return strategys;
	}

	public void setStrategys(List<StrategyVO> strategys) {
		this.strategys = strategys;
	}

}
