package reeman.elevator.demo.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import reeman.elevator.demo.R;
import reeman.elevator.demo.adapter.LiftsSelectAdapter;
import reeman.elevator.demo.model.config.ConfigResponse;
import reeman.elevator.demo.model.config.Lifts;


public class LiftsFragment extends Fragment {

    public LiftsFragment() {
        super(R.layout.fragment_lifts);
    }

    LiftsSelectAdapter liftsSelectAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.lifts_select_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        liftsSelectAdapter = new LiftsSelectAdapter();
        if(getArguments() != null) {
            ConfigResponse configResponse = (ConfigResponse) getArguments().getSerializable("config");
            liftsSelectAdapter.setDataList(configResponse.getData().getGroups().get(0).getLifts());
        }
        liftsSelectAdapter.setOnItemClickListener(new LiftsSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Lifts lifts) {
                Bundle bundle = new Bundle();
                bundle.putAll(getArguments());
                bundle.putSerializable("lift", lifts);
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_liftsFragment_to_startFloorFragment, bundle);
            }
        });
        recyclerView.setAdapter(liftsSelectAdapter);
    }
}
