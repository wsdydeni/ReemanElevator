package reeman.elevator.demo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.toast.ToastUtils;
import com.tencent.mmkv.MMKV;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import reeman.elevator.demo.fragment.LoginFragment;
import reeman.elevator.demo.model.ConfigOrActionsRequest;
import reeman.elevator.demo.model.ConnectResponse;
import reeman.elevator.demo.model.actions.ActionsResponse;
import reeman.elevator.demo.model.actions.CallTypes;
import reeman.elevator.demo.model.call.DestinationCall;
import reeman.elevator.demo.model.call.DestinationCallRequest;
import reeman.elevator.demo.model.call.DestinationResponse;
import reeman.elevator.demo.model.call.DestinationCallPayload;
import reeman.elevator.demo.model.config.ConfigResponse;
import reeman.elevator.demo.model.config.Destinations;
import reeman.elevator.demo.model.config.Floors;
import reeman.elevator.demo.model.config.Lifts;
import reeman.elevator.demo.model.monitor.LiftDeckPositionStatus;
import reeman.elevator.demo.model.monitor.LiftDoorsStatus;
import reeman.elevator.demo.model.monitor.MonitorPayload;
import reeman.elevator.demo.model.monitor.MonitorRequest;
import reeman.elevator.demo.model.monitor.MonitorResponse;
import reeman.elevator.demo.model.monitor.MonitorResponse2;
import reeman.elevator.demo.model.resources.Buildings;
import reeman.elevator.demo.utils.LogUtil;
import reeman.elevator.demo.utils.ProgressDialogUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public final Handler handler = new Handler(Looper.getMainLooper());

    public static final Gson gson = new Gson();

    private boolean isBind = false;

    public WebsocketService websocketService;

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBind = true;
            websocketService = ((WebsocketService.WebsocketServiceBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, WebsocketService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        websocketService = null;
        if(isBind) unbindService(serviceConnection);
    }

    private Boolean isFirstConnect = true;

    private Boolean isConnect = false;

    WebSocket mWebsocket;

    private Buildings mBuilding;
    private ConfigResponse configResponse;
    private ActionsResponse actionsResponse;

    private boolean isWaitConfig = false;
    private boolean isWaitConfig2 = false;

    private boolean isWaitActions = false;
    private boolean isWaitActions2 = false;

    private boolean isWaitDestination = false;
    private boolean isWaitDestination2 = false;

    public boolean isWaitMonitor = false;

    private static final String regexDoors = "lift_[^/]+/doors";
    private static final String regexDeckPosition = "lift_[^/]+/position";

    public interface OnLiftFloorChangeListener {
        void onCurFloorChange(int curFloor);
    }

    private OnLiftFloorChangeListener onLiftFloorChangeListener;

    public void setOnLiftFloorChangeListener(OnLiftFloorChangeListener onLiftFloorChangeListener) {
        this.onLiftFloorChangeListener = onLiftFloorChangeListener;
    }

    public interface OnLiftDoorStateChangeListener {
        void onDoorStateChange(String floor, String state);
    }

    private OnLiftDoorStateChangeListener onLiftDoorStateChangeListener;

    public void setOnLiftDoorStateChangeListener(OnLiftDoorStateChangeListener onLiftDoorStateChangeListener) {
        this.onLiftDoorStateChangeListener = onLiftDoorStateChangeListener;
    }

    private final WebSocketListener webSocketListener = new WebSocketListener() {
        @Override
        public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
            Log.d("test", "onClosed reason: " + reason);
            isConnect = false;
        }

        @Override
        public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
            Log.d("test", "onClosing reason: " + reason);
        }

        @Override
        public void onFailure(@NonNull WebSocket webSocket, @Nullable Throwable throwable, @Nullable Response response) {
            if (response != null) {
                if (response.body() != null) {
                    Log.d("test", "onFailure response: " + response.body());
                }
            }
            if(throwable != null) {
                ToastUtils.show(throwable.getMessage());
            }
            dismissLoading(MainActivity.this);
        }

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            LogUtil.print(Log.INFO, "test", "onMessage: " + text);
            if(isWaitConfig2) {
                Log.d("test", "isWaitConfig2");
                isWaitConfig2 = false;
                configResponse = gson.fromJson(text, ConfigResponse.class);
                /*
                 * 获取Actions
                 */
                String type = "common-api";
                String requestId = UUID.randomUUID().toString();
                String buildingId = "building:" + mBuilding.getId();
                String callType = "actions";
                String groupId = mBuilding.getGroups().get(0).getId().replace(mBuilding.getId()+ ":", "");
                ConfigOrActionsRequest configOrActionsRequest = new ConfigOrActionsRequest(type, requestId, buildingId, callType, groupId);
                isWaitActions = true;
                mWebsocket.send(gson.toJson(configOrActionsRequest));
                return;
            }
            if(isWaitActions2) {
                Log.d("test", "isWaitActions2");
                isWaitActions2 = false;
                List<Lifts> lifts = configResponse.getData().getGroups().get(0).getLifts();
                List<Floors> floors = lifts.get(lifts.size() - 1).getFloors();
                int floor = floors.get(floors.size() - 1).getGroup_floor_id();
                actionsResponse = gson.fromJson(text, ActionsResponse.class);
                Bundle bundle = new Bundle();
                bundle.putInt("floors", floor);
                bundle.putSerializable("config", configResponse);
                bundle.putSerializable("building", mBuilding);
                bundle.putSerializable("actions", actionsResponse);
                dismissLoading(MainActivity.this);
                handler.post(() -> Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment)
                        .navigate(R.id.action_buildingFragment_to_liftsFragment, bundle));
                return;
            }
            if(isWaitDestination2) {
                Log.d("test", "isWaitDestination2");
                isWaitDestination2 = false;
                DestinationResponse destinationResponse = gson.fromJson(text, DestinationResponse.class);
                dismissLoading(MainActivity.this);
                if(!destinationResponse.getData().isSuccess()) {
                    ToastUtils.show(destinationResponse.getData().getError());
                    return;
                }
                /*
                 * Site Monitoring
                 */
                String type = "site-monitoring";
                String requestId = UUID.randomUUID().toString();
                String buildingId = "building:" + mBuilding.getId();
                String callType = "monitor";
                String groupId = mBuilding.getGroups().get(0).getId().replace(mBuilding.getId()+ ":", "");
                MonitorPayload monitorPayload = new MonitorPayload();
                monitorPayload.setSub(MMKV.defaultMMKV().getString(LoginFragment.KEY_AccessToken, ""));
                // TODO monitor payload duration
                monitorPayload.setDuration(300);
                String lift_status_topic = "lift_" + mLiftID + "/status";
                String lift_next_stop_eta_topic = "lift_" + mLiftID + "/next_stop_eta";
                String lift_position_topic = "lift_" + mLiftID + "/position";
                String lift_stopping_topic = "lift_" + mLiftID + "/stopping";
                String lift_doors_topic = "lift_" + mLiftID + "/doors";
                String call_state_topic = "call_state/" + destinationResponse.getData().getSession_id() +"/fixed";
                List<String> topicList = new ArrayList<>();
//                topicList.add(lift_status_topic);
                topicList.add(lift_next_stop_eta_topic);
                topicList.add(lift_position_topic);
//                topicList.add(lift_stopping_topic);
                topicList.add(lift_doors_topic);
//                topicList.add(call_state_topic);
                Log.d("test", "monitor-topics: " + topicList);
                monitorPayload.setSubtopics(topicList);
                MonitorRequest monitorRequest = new MonitorRequest();
                monitorRequest.setType(type);
                monitorRequest.setRequestId(requestId);
                monitorRequest.setBuildingId(buildingId);
                monitorRequest.setCallType(callType);
                monitorRequest.setGroupId(groupId);
                monitorRequest.setPayload(monitorPayload);
                mWebsocket.send(gson.toJson(monitorRequest));
                return;
            }
            if(isWaitMonitor) {
                Log.d("test", "isWaitMonitor");
                MonitorResponse monitorResponse = gson.fromJson(text, MonitorResponse.class);
                if(monitorResponse.getSubtopic().matches(regexDoors)) {
                    MonitorResponse2<LiftDoorsStatus> monitorResponse2 = gson.fromJson(text, new TypeToken<MonitorResponse2<LiftDoorsStatus>>(){}.getType());
                    LiftDoorsStatus liftDoorsStatus = monitorResponse2.getData();
                    handler.postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if(onLiftDoorStateChangeListener != null) {
                                onLiftDoorStateChangeListener.onDoorStateChange(String.valueOf(liftDoorsStatus.getLanding()).replace("0", ""), liftDoorsStatus.getState());
                            }
                        }
                    });
                    if(String.valueOf(liftDoorsStatus.getLanding()).equals(startFloor + "000") && liftDoorsStatus.getState().equals("OPENED")) {

                        // TODO arrived start floor,door is OPENED, keep door open
                        return;
                    }
                    if(String.valueOf(liftDoorsStatus.getLanding()).equals(endFloor + "000") && liftDoorsStatus.getState().equals("OPENED")) {
                        // TODO arrived target floor,door is OPENED, keep door open
                        return;
                    }
                }
                if(monitorResponse.getSubtopic().matches(regexDeckPosition)) {
                    MonitorResponse2<LiftDeckPositionStatus> monitorResponse2 = gson.fromJson(text, new TypeToken<MonitorResponse2<LiftDeckPositionStatus>>(){}.getType());
                    LiftDeckPositionStatus liftDeckPositionStatus = monitorResponse2.getData();
                    handler.postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if(onLiftFloorChangeListener != null) {
                                onLiftFloorChangeListener.onCurFloorChange(liftDeckPositionStatus.getCur());
                            }
                        }
                    });
                }
                return;
            }
            ConnectResponse connectResponse = gson.fromJson(text, ConnectResponse.class);
            Log.d("test", "socket code: " + connectResponse.getStatusCode());
            if(connectResponse.getStatusCode() == 201) {
                if(isWaitConfig) {
                    Log.d("test", "isWaitConfig");
                    isWaitConfig = false;
                    isWaitConfig2 = true;
                    return;
                }
                if(isWaitActions) {
                    Log.d("test", "isWaitActions");
                    isWaitActions = false;
                    isWaitActions2 = true;
                    return;
                }
                if(isWaitDestination) {
                    Log.d("test", "isWaitDestination");
                    isWaitDestination = false;
                    isWaitDestination2 = true;
                    return;
                }
                isWaitMonitor = true;
            }else {
                dismissLoading(MainActivity.this);
            }
        }

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {}

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
            mWebsocket = webSocket;
            if(isFirstConnect) {
                /*
                 * 获取Config
                 */
                getConfig();
            }
            isFirstConnect = false;
            isConnect = true;
        }
    };

    private void getConfig() {
        String type = "common-api";
        String requestId = UUID.randomUUID().toString();
        String buildingId = "building:" + mBuilding.getId();
        String callType = "config";
        String groupId = mBuilding.getGroups().get(0).getId().replace(mBuilding.getId()+ ":", "");
        ConfigOrActionsRequest configOrActionsRequest = new ConfigOrActionsRequest(type, requestId, buildingId, callType, groupId);
        isWaitConfig = true;
        mWebsocket.send(gson.toJson(configOrActionsRequest));
    }

    public void connectWebSocket(Buildings buildings) {
        if(websocketService != null) {
            ProgressDialogUtils.showProgressDialog(this, "loading config");
            if(isConnect) {
                // 已经连接不再连接
                getConfig();
                return;
            }
            String hostname = "wss://dev.kone.com/stream-v2";
            String token = MMKV.defaultMMKV().getString(LoginFragment.KEY_AccessToken, "");
            mBuilding = buildings;
            websocketService.connectWebSocket(hostname, token, webSocketListener);
        }
    }

    private String startFloor;
    private String endFloor;
    private int mLiftID = -1;

    public void destinationCall(String startFloor, String endFloor, int liftId) {
        ProgressDialogUtils.showProgressDialog(MainActivity.this, "calling");
        String type = "lift-call-api-v2";
        String buildingId = "building:" + mBuilding.getId();
        String callType = "action";
        String groupId = mBuilding.getGroups().get(0).getId().replace(mBuilding.getId()+ ":", "");
        DestinationCall destinationCall = new DestinationCall(getDestinationCallActionId(), getDestinationFloorAreaId(Integer.parseInt(endFloor)));
        // TODO terminal dynamic in config
        DestinationCallPayload destinationCallPayload = new DestinationCallPayload(generateRandomNumber(9), getDestinationFloorAreaId(Integer.parseInt(startFloor)), getTimeFormatStr(), 1, destinationCall);
        DestinationCallRequest destinationCallRequest = new DestinationCallRequest(type, buildingId, callType, groupId, destinationCallPayload);
        LogUtil.print(Log.INFO, "test-call", destinationCallRequest.toString());
        isWaitDestination = true;
        this.startFloor = startFloor;
        this.endFloor = endFloor;
        this.mLiftID = liftId;
        mWebsocket.send(gson.toJson(destinationCallRequest));
    }

    public static int generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // 生成0到9之间的随机数
            sb.append(digit);
        }

        return Integer.parseInt(sb.toString());
    }

    private String getTimeFormatStr() {
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Riyadh");
        Calendar calendar = Calendar.getInstance(timeZone);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        format.setCalendar(calendar);

        return format.format(calendar.getTime());
    }

    private int getDestinationFloorAreaId(int floor) {
        Optional<Destinations> destinations = Arrays.stream(configResponse.getData().getDestinations().toArray(new Destinations[0]))
                .filter(destination -> destination.getGroup_floor_id() == floor)
                .findFirst();
        int areaId = -1;
        if(destinations.isPresent()) {
            areaId = destinations.get().getArea_id();
        }
        return areaId;
    }

    private int getDestinationCallActionId() {
        Optional<CallTypes> callTypes = Arrays.stream(actionsResponse.getData().getCall_types().toArray(new CallTypes[0]))
                .filter(actionsCallType -> actionsCallType.getAction_type().equals("DestinationCall"))
                .findFirst();
        int destinationCallActionId = -1;
        if(callTypes.isPresent()) {
            destinationCallActionId = callTypes.get().getAction_id();
        }
        return destinationCallActionId;
    }

    private static void dismissLoading(MainActivity mainActivity) {
        mainActivity.handler.postAtFrontOfQueue(ProgressDialogUtils::hideProgressDialog);
    }
}