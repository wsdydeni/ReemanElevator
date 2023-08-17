package reeman.elevator.demo.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import reeman.elevator.demo.R;
import reeman.elevator.demo.adapter.FloorSelectAdapter;

/**
 * 乘梯选择楼层界面
 */
public class StartFloorFragment extends Fragment {

    public StartFloorFragment() {
        super(R.layout.fragment_start_floor);
    }

    int floors;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            floors = getArguments().getInt("floors", 0);
        }
        RecyclerView recyclerView = view.findViewById(R.id.end_floor_select_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        FloorSelectAdapter floorSelectAdapter = new FloorSelectAdapter();
        floorSelectAdapter.setDataList(generateStringList(floors));
        floorSelectAdapter.setOnItemClickListener(new FloorSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String floor) {
                Bundle bundle = new Bundle();
                bundle.putAll(getArguments());
                bundle.putString("startFloor", floor);
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_startFloorFragment_to_endFloorFragment, bundle);
            }
        });
        recyclerView.setAdapter(floorSelectAdapter);
    }

    public static List<String> generateStringList(int num) {
        List<String> stringList = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            stringList.add(String.valueOf(i));
        }
        return stringList;
    }
}
