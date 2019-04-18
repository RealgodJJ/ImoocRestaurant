package mybaidu.admin.example.com.imoocrestaurant.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mybaidu.admin.example.com.imoocrestaurant.R;
import mybaidu.admin.example.com.imoocrestaurant.bean.Order;
import mybaidu.admin.example.com.imoocrestaurant.utils.Config;

/**
 * Created by RealgodJJ
 * on 2019/4/15
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderItemViewHolder> {
    private List<Order> orderList;
    private Context context;
    private LayoutInflater inflater;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.orderList = orderList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = inflater.inflate(R.layout.order_list_item, viewGroup, false);
        return new OrderItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder orderItemViewHolder, int i) {
        Order order = orderList.get(i);
        Picasso.get().load(Config.baseUrl + order.getRestaurant().getIcon())
                .placeholder(R.drawable.pictures_no).into(orderItemViewHolder.ivProduct);

//        Log.d("RealgodJJJJJJJJJJJJJJJjj", String.valueOf(order.productVos.size()));

        orderItemViewHolder.tvRestaurantName.setText(order.getRestaurant().getName());
        if (order.productVos.size() > 0)
            orderItemViewHolder.tvLabel.setText(context.getString(R.string.order_description,
                    order.productVos.get(0).product.getName(), order.getCount()));
        orderItemViewHolder.tvPrice.setText(context.getString(R.string.total_cost, order.getPrice()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        TextView tvRestaurantName, tvLabel, tvPrice;


        OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.iv_product);
            tvRestaurantName = itemView.findViewById(R.id.tv_restaurant_name);
            tvLabel = itemView.findViewById(R.id.tv_label);
            tvPrice = itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                }
            });
        }
    }
}
