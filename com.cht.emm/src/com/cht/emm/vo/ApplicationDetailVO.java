package com.cht.emm.vo;

import java.util.List;

import com.cht.emm.model.id.ApplicationDeploy;


public class ApplicationDetailVO {

	private ApplicationVO app;
	private List<ApplicationScoreVO> scores;
	private List<ApplicationDeploy> deploys;
	private List<ApplicationVO> versions;

	public ApplicationVO getApp() {
		return app;
	}

	public void setApp(ApplicationVO app) {
		this.app = app;
	}

	public List<ApplicationScoreVO> getScores() {
		return scores;
	}

	public void setScores(List<ApplicationScoreVO> scores) {
		this.scores = scores;
	}

	public List<ApplicationDeploy> getDeploys() {
		return deploys;
	}

	public void setDeploys(List<ApplicationDeploy> deploys) {
		this.deploys = deploys;
	}

	public List<ApplicationVO> getVersions() {
		return versions;
	}

	public void setVersions(List<ApplicationVO> versions) {
		this.versions = versions;
	}

}
