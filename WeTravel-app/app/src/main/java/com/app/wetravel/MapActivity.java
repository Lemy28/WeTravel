package com.app.wetravel;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.ServiceSettings;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;

public class MapActivity extends AppCompatActivity {

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private double latitude;
    private double longititude;


    private double getLatitude(GeocodeAddress geocodeAddress) {
        return geocodeAddress.getLatLonPoint().getLatitude();
    }

    private double getLongitude(GeocodeAddress geocodeAddress) {
        return geocodeAddress.getLatLonPoint().getLongitude();
    }
    public interface GeocodeResultListener {
        void onGeocodeResult(double latitude, double longitude);
    }

    private MapView mapView;

    //地图控制器
    private AMap aMap = null;
    //位置更改监听
    private LocationSource.OnLocationChangedListener mListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        View button = findViewById(R.id.navibutton);

        Intent intent = getIntent();

        String location = intent.getStringExtra("key"); // 根据键获取数据，数据类型根据实际情况确定

        MapsInitializer.updatePrivacyShow(this, true, true);
        MapsInitializer.updatePrivacyAgree(this, true);
        ServiceSettings.updatePrivacyShow(this, true, true);
        ServiceSettings.updatePrivacyAgree(this, true);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        AMap aMap = null;
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        try {
            AMap finalAMap = aMap;
            AMap finalAMap1 = aMap;
            getLatlon(location, new GeocodeResultListener() {
                @Override
                public void onGeocodeResult(double latitude, double longitude) {
                    LatLng latLng = new LatLng(latitude,longitude);

                    Uri uri = Uri.parse("androidamap://navi?sourceApplication=appname&poiname="+location+"&lat="+latitude+"&lon="+longitude+"&dev=1&style=2");
                    final Marker marker = finalAMap.addMarker(new MarkerOptions().position(latLng).title(location).snippet(""));
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                    marker.showInfoWindow();
                    finalAMap1.moveCamera(cameraUpdate);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);

                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
                }
            });
        } catch (AMapException e) {
            throw new RuntimeException(e);
        }
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);


    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }
    @Override
    protected void onResume () {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }
    @Override
    protected void onPause () {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState (Bundle outState){
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }
    private void getLatlon(String Name,GeocodeResultListener listener) throws AMapException {

        ServiceSettings.updatePrivacyShow(this,true,true);
        ServiceSettings.updatePrivacyAgree(this,true);
        GeocodeSearch geocodeSearch=new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                if (i==1000){
                    if (geocodeResult!=null && geocodeResult.getGeocodeAddressList()!=null &&
                            geocodeResult.getGeocodeAddressList().size()>0){
                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                        latitude = getLatitude(geocodeAddress);//纬度
                        longititude = getLongitude(geocodeAddress);//经度
                        String adcode= geocodeAddress.getAdcode();//区域编码
                        listener.onGeocodeResult(latitude, longititude);

                        Log.e("地理编码", adcode+"");
                        Log.e("纬度latitude",latitude+"");
                        Log.e("经度longititude",longititude+"");


                    }else {

                    }
                }
            }

        });
        GeocodeQuery geocodeQuery=new GeocodeQuery(Name.trim(),null);
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
    }

}
