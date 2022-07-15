package hai2022.team.busapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hai2022.team.busapplication.R;
import hai2022.team.busapplication.interfaces.UserCallback;
import hai2022.team.busapplication.models.User;

public class UserRecyclerviewAdapter extends RecyclerView.Adapter<UserRecyclerviewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<User> users;
    private int res;
    private UserCallback callback;

    public UserRecyclerviewAdapter(Context context, ArrayList<User> users, int res, UserCallback callback) {
        this.context = context;
        this.users = users;
        this.res = res;
        this.callback = callback;
        Log.d("Constructoradapter", "yes");
    }

    @NonNull
    @Override
    public UserRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecyclerviewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (position == 0) {
            holder.iv_user.setImageResource(R.drawable.add_user);
            holder.iv_user.getLayoutParams().height = 200;
            holder.iv_user.getLayoutParams().width = 200;
        }
        else
            holder.iv_user.setImageResource(users.get(position).getImg());
        if (users.get(position).getImg() == 0) {
            holder.iv_user.setImageResource(R.drawable.profile);
        }
        holder.tv_username.setText(users.get(position).getUsername());
        Log.d("onbindadapter", "yes");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    callback.add_new_user(users.get(position + 1).getType());
                } else {
                    callback.user_click_listener(users.get(position));
                }
            }
        });

        try {
            if (users.get(position).getType().equals("driver")||users.get(position).getType().equals("student"))
                holder.ib_del.setVisibility(View.VISIBLE);
            if (users.get(position).getType().equals("driver")){
                holder.iv_user.setImageResource(R.drawable.drivers);
            }else if (users.get(position).getType().equals("student")){
                holder.iv_user.setImageResource(R.drawable.student);
            }
        }catch (Exception e){
            
        }

        holder.ib_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.remove_user(users.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
//        return users.size() < 5 ? users.size() : 5;
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_user;
        TextView tv_username;
        ImageButton ib_del;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bind(itemView);
        }

        private void bind(View itemView) {
            iv_user = itemView.findViewById(R.id.recyclerview_person_iv);
            ib_del = itemView.findViewById(R.id.recyclerview_person_btn_delete);
            tv_username = itemView.findViewById(R.id.recyclerview_person_tv);
            Log.d("bindadapter", "yes");

        }
    }
}
