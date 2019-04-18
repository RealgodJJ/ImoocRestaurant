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
import mybaidu.admin.example.com.imoocrestaurant.utils.Config;
import mybaidu.admin.example.com.imoocrestaurant.utils.Toasts;
import mybaidu.admin.example.com.imoocrestaurant.vo.ProductItem;

/**
 * Created by RealgodJJ
 * on 2019/4/18
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<ProductItem> productItemList;

    public ProductAdapter(Context context, List<ProductItem> productItemList) {
        this.context = context;
        this.productItemList = productItemList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = inflater.inflate(R.layout.product_list_item, viewGroup, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        ProductItem productItem = productItemList.get(i);

        Picasso.get().load(Config.baseUrl + productItem.getIcon())
                .placeholder(R.drawable.pictures_no).into(productViewHolder.ivProduct);
        productViewHolder.tvProductName.setText(productItem.getName());
        productViewHolder.tvCount.setText(String.valueOf(productItem.getCount()));
        productViewHolder.tvLabel.setText(productItem.getLabel());
        productViewHolder.tvPrice.setText(
                context.getString(R.string.single_product_price, productItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return productItemList.size();
    }

    public interface OnProductListener {
        void onProductAdd(ProductItem productItem);

        void onProductSub(ProductItem productItem);
    }

    private OnProductListener onProductListener;

    public void setOnProductListener(OnProductListener onProductListener) {
        this.onProductListener = onProductListener;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct, ivAdd, ivSub;
        TextView tvProductName, tvLabel, tvPrice, tvCount;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.iv_product);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvLabel = itemView.findViewById(R.id.tv_label);
            tvPrice = itemView.findViewById(R.id.tv_price);
            ivAdd = itemView.findViewById(R.id.iv_add);
            ivSub = itemView.findViewById(R.id.iv_sub);
            tvCount = itemView.findViewById(R.id.tv_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO:进入商品详情页面
                }
            });

            ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //修改菜品数目
                    int position = getLayoutPosition();
                    ProductItem productItem = productItemList.get(position);
                    productItem.setCount(productItem.getCount() + 1);
                    //修改UI
                    tvCount.setText(String.valueOf(productItem.getCount()));
                    //回调到Activity
                    if (onProductListener != null) {
                        onProductListener.onProductAdd(productItem);
                    }
                }
            });

            ivSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //修改菜品数目
                    int position = getLayoutPosition();
                    ProductItem productItem = productItemList.get(position);
                    if (productItem.getCount() == 0) {
                        Toasts.showToast("菜品数量不得小于0！");
                        return;
                    }
                    productItem.setCount(productItem.getCount() - 1);
                    //修改UI
                    tvCount.setText(String.valueOf(productItem.getCount()));
                    //回调到Activity
                    if (onProductListener != null) {
                        onProductListener.onProductSub(productItem);
                    }
                }
            });
        }
    }
}
