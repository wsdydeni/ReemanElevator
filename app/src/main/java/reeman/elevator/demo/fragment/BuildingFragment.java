package reeman.elevator.demo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.toast.ToastUtils;
import com.tencent.mmkv.MMKV;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import reeman.elevator.demo.MainActivity;
import reeman.elevator.demo.R;
import reeman.elevator.demo.adapter.BuildingSelectAdapter;
import reeman.elevator.demo.model.ErrorMessageResult;
import reeman.elevator.demo.model.resources.BuildingResult;
import reeman.elevator.demo.model.resources.Buildings;
import reeman.elevator.demo.utils.ProgressDialogUtils;

/**
 * 获取建筑界面
 */
public class BuildingFragment extends Fragment {
    public BuildingFragment() {
        super(R.layout.fragment_building);
    }

    BuildingResult buildingResult;

    BuildingSelectAdapter buildingSelectAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.end_floor_select_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        buildingSelectAdapter = new BuildingSelectAdapter();
        if(getArguments() != null) {
            buildingResult = (BuildingResult) getArguments().getSerializable("building");
            buildingSelectAdapter.setDataList(buildingResult.getBuildings());
        }
        buildingSelectAdapter.setOnItemClickListener(new BuildingSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Buildings buildings) {
                MainActivity mainActivity = (MainActivity) requireActivity();
                mainActivity.connectWebSocket(buildings);
            }
        });
        recyclerView.setAdapter(buildingSelectAdapter);
    }

    private final Callback buildingCallback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            ToastUtils.show("get Building Resource failed");
            MainActivity mainActivity = (MainActivity) requireActivity();
            dismissLoading(mainActivity);
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            MainActivity mainActivity = (MainActivity) requireActivity();
            if(response.code() == 200) {
                if(response.body() != null) {
                    BuildingResult buildingResult = MainActivity.gson.fromJson(response.body().string(), BuildingResult.class);
                    if(buildingResult.getBuildings().isEmpty()) {
                        dismissLoading(mainActivity);
                        ToastUtils.show("buildings is empty");
                        return;
                    }
                    mainActivity.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            dismissLoading(mainActivity);
                            buildingSelectAdapter.setDataList(buildingResult.getBuildings());
                        }
                    });
                }
            }else {
                if(response.body() != null) {
                    ErrorMessageResult errorMessageResult = MainActivity.gson.fromJson(response.body().string(), ErrorMessageResult.class);
                    ToastUtils.show(errorMessageResult.getMessage());
                    dismissLoading(mainActivity);
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if(getArguments() == null) {
            String token = MMKV.defaultMMKV().getString(LoginFragment.KEY_AccessToken, "");
            Log.d("test", "token: " + token);
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.websocketService.getListResources(token, buildingCallback);
        }
    }

    private static void dismissLoading(MainActivity mainActivity) {
        mainActivity.handler.post(ProgressDialogUtils::hideProgressDialog);
    }
}
