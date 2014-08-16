package com.pkumap.eventlistener;

import java.util.ArrayList;

import com.pkumap.activity.MapActivity;
import com.pkumap.activity.PathActivity;
import com.pkumap.activity.PathPlanActivity;
import com.pkumap.bean.Poi;
import com.pkumap.bean.Point;
import com.pkumap.bean.Road;
import com.pkumap.bean.RoadNode;
import com.pkumap.util.BuildingManager;
import com.pkumap.util.Dijkstra;
import com.pkumap.util.PathPlanManager;
import com.pkumap.util.PoiManager;
import com.pkumap.util.RoadPlan;
import com.zdx.pkumap.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class PathPlanOnClickListener implements OnClickListener {
	private PathPlanActivity event_Activity;
	private EditText path_start_edit=null;
	private EditText path_end_edit=null;
	private PathPlanManager pathPlanManager;
	private ArrayList<RoadNode> roadNodes;
	private ArrayList<Road> roads;
	/**
	 * 总共的点数
	 */
	private final int pCount = 1000;
	/**
	 * 初始化地图信息
	 */
	private final int INF = 999999;
	/**
	 * 路径规划中的邻接表
	 */
	private float[][] pathmap;
	private Dijkstra dijkstra;
	public PathPlanOnClickListener(PathPlanActivity target_Activity){
		this.event_Activity=target_Activity;
		this.path_start_edit=(EditText) event_Activity.findViewById(R.id.path_start);
		this.path_end_edit=(EditText) event_Activity.findViewById(R.id.path_end);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.path_return:
			event_Activity.finish();
			break;
		case R.id.path_search:
			getStartEndPoint();
			break;
		case R.id.btn_import:
			importPoiData();
			break;
		}
	}
	/**
	 * 返回起点和终点信息，在地图上画出路径
	 */
	private void getStartEndPoint(){
		
		RoadPlan roadPlan=new RoadPlan(event_Activity.pathPlanManager,event_Activity.poiManager,event_Activity.buildingManager);
		String startStr=path_start_edit.getText().toString();
		String endStr=path_end_edit.getText().toString();
		ArrayList<RoadNode> roadNodes=roadPlan.getRoadNodes(startStr,endStr,event_Activity.map_type);
		
		Bundle bundle=new Bundle();
		bundle.putParcelableArrayList("path", roadNodes);
		
		Intent intent=new Intent();
		intent.setClass(event_Activity,MapActivity.class);
//		intent.setClass(event_Activity, PathActivity.class);  //在新的Activity上显示路线
//		event_Activity.startActivity(intent);
		intent.putExtras(bundle);

		event_Activity.setResult(event_Activity.RESULT_PATHPLAN, intent);
		event_Activity.finish();
	}
	
	private void importPoiData(){
		//POI数据操作
//		PoiManager poiManager=new PoiManager(event_Activity.getApplicationContext());
//		poiManager.importPoiFromMongo();
//		poiManager.updatePoiTable();
//		poiManager.updatePoiAddGps();
//		poiManager.ConvertToGpsAndUpdateGpsInPoi();
//		Poi poi=poiManager.getPoiById(255);
//		poiManager.updatePoiAddPointID();
		//路径规划数据操作
		PathPlanManager pathPlanManager=new PathPlanManager(event_Activity.getApplicationContext());
//		pathPlanManager.importRoadInfo();
//		pathPlanManager.importRoadNodeInfo();
//		pathPlanManager.updateRoadNodeTableAddGps();
		pathPlanManager.ConvertGpsFromLocalAndUpdateRoadNode();

		//Building数据操作
//		BuildingManager buildingManager=new BuildingManager(event_Activity.getApplicationContext());
//		buildingManager.createBuildingTable();
//		buildingManager.importBuildingData();
//		buildingManager.getBuildingByName("动物房");
	}
}
