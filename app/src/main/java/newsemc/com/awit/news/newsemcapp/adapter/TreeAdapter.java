package newsemc.com.awit.news.newsemcapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.interfaces.Callback;
import newsemc.com.awit.news.newsemcapp.tree.TreeElement;

/**
 * Created by Administrator on 15-10-29.
 */
public class TreeAdapter  extends ArrayAdapter implements Callback {
//    private Bitmap mIconCollapse;
//    private Bitmap mIconExpand;
//    private Bitmap NoChlid;
//    private LayoutInflater mInflater;
//    private ArrayList<TreeElement> mfilelist;
//
//    class ViewHolder{
//        TextView text;
//        ImageView icon;
//    }
//
//    public TreeAdapter(Context context, int textViewResourceId, ArrayList objects) {
//        super(context, textViewResourceId, objects);
//        mIconCollapse = BitmapFactory.decodeResource(
//                context.getResources(), R.drawable.outline_list_collapse);
//        mIconExpand = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.outline_list_expand);
//        NoChlid = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.outline_list_expand);
//
//    }
//
//
//    @Override
//    public int getCount() {
//        return super.getCount();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return super.getItem(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return super.getItemId(position);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//
//        if (convertView==null){
//            convertView=mInflater.inflate(R.layout.outline,null);
//            holder = new ViewHolder();
//            holder.text=(TextView)convertView.findViewById(R.id.text);
//            holder.icon=(ImageView)convertView.findViewById(R.id.icon);
//            convertView.setTag(holder);
//        }else
//        {
//            holder=(ViewHolder)convertView.getTag();
//        }
//        int level=mfilelist.get(position).getLevel();//每次根据节点的层次显示节点的位置
//        holder.icon.setPadding(25 * (level + 1), holder.icon
//                .getPaddingTop(), 0, holder.icon.getPaddingBottom());
//
//        holder.icon.setVisibility(View.VISIBLE);
//        holder.text.setText(mfilelist.get(position).getOutlineTitle());
//        //如果有子节点而且当前展开的图标不是+的图标的时候
//        if (mfilelist.get(position).isMhasChild()
//                && mfilelist.get(position).isExpand()==false){
//                holder.icon.setImageBitmap(mIconCollapse);
//        }else if(mfilelist.get(position).isMhasChild()
//                && mfilelist.get(position).isExpand()==true){
//            //如果有子节点而且当前展开的图标不是+的图标的时候
//                holder.icon.setImageBitmap(mIconExpand);
//        }else if(!mfilelist.get(position).isMhasChild()){
//                holder.icon.setImageBitmap(mIconCollapse);
//                holder.icon.setVisibility(View.VISIBLE);
//        }
//        return super.getView(position, convertView, parent);
//    }
        private LayoutInflater mInflater;
        private ArrayList<TreeElement> mfilelist;
        private Bitmap mIconCollapse;
        private Bitmap mIconExpand;
        private Bitmap NoChlid;
        private int textViewResourceId;
        private Context context;



        class ViewHolder {
            TextView text;
            ImageView icon;
        }
        public TreeAdapter(Context context, int textViewResourceId, ArrayList objects) {
            super(context, textViewResourceId, objects);
            mInflater = LayoutInflater.from(context);
            mfilelist = objects;
            this.textViewResourceId = textViewResourceId;
            mIconCollapse = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.tree_ec);
            mIconExpand = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.tree_ex);
            NoChlid = BitmapFactory.decodeResource(context.getResources(),R.drawable.tree_ex);
        }

    public int getCount() {
        return mfilelist.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public String getView(String convertView){
        return convertView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        convertView = mInflater.inflate(R.layout.outline, null);
        holder = new ViewHolder();
        holder.text = (TextView) convertView.findViewById(R.id.text);
        holder.icon = (ImageView) convertView.findViewById(R.id.icon);
        convertView.setTag(holder);


        TreeElement treeElement = mfilelist.get(position);
        final int mLevel = treeElement.getLevel();

        final TreeElement obj = mfilelist.get(position);
        int level = obj.getLevel();

        holder.icon.setPadding(25 * (level + 1), holder.icon
                .getPaddingTop(), 0, holder.icon.getPaddingBottom());
        holder.text.setText(obj.getOutlineTitle());




        if(treeElement.isMhasChild()){
            if(treeElement.isExpanded()){
                holder.icon.setImageBitmap(mIconExpand);
//                holder.icon.setOnClickListener(new TreeElementIconClickListener(
//                        context, mfilelist, this, treeElement.getLevel()));
            }else {
                holder.icon.setImageBitmap(mIconCollapse);
            }

            if(treeElement.getCaptionOnClickListener() != null){
                holder.text.setTag(treeElement.getValue());
                // holder.text.setOnClickListener(treeElement.getCaptionOnClickListener());
            }
        }else {
            holder.icon.setImageBitmap(mIconExpand);
            holder.icon.setVisibility(View.INVISIBLE);
            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(obj.getId());
                    String treeId = obj.getId();
                    String treeNodeStr = obj.getOutlineTitle();
                    System.out.println(treeNodeStr);
                    //                ArrayList<TreeElement> tempList = obj.getChildList();
//              //一级级遍历
//                for (int i = 0; i < tempList.size(); i++) {
//                    TreeElement element = tempList.get(i);
//                    element.setLevel(nextLevel);
//                    element.setExpanded(true);
//                    treeElementlist.add(position + i + 1, element);
//                    for(TreeElement treeElement:element.getChildList()){
//                        treeElement.setLevel(nextLevel);
//                        treeElement.setExpanded(true);
//                        treeElementlist.add(position+1,treeElement);
//                    }
//                }
                }
            });
        }

//
//



//        if (obj.isMhasChild() && (obj.isExpanded() == false)) {
//            holder.icon.setImageBitmap(mIconCollapse);
//        } else if (obj.isMhasChild() && (obj.isExpanded() == true)) {
//            holder.icon.setImageBitmap(mIconExpand);
//        } else if (!obj.isMhasChild()) {
//            holder.icon.setImageBitmap(mIconCollapse);
//            holder.icon.setVisibility(View.INVISIBLE);
//            holder.text.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    System.out.println(obj.getId());
//                    String treeId = obj.getId();
//                    String treeNodeStr = obj.getOutlineTitle();
//                    System.out.println(treeNodeStr);
//                }
//            });
//            if(treeElement.getCaptionOnClickListener() != null){
//                holder.text.setTag(treeElement.getValue());
//                holder.text.setOnClickListener(treeElement.getCaptionOnClickListener());
//           }
        //   }

        return convertView;
    }

//    public static class TreeElementIconClickListener implements View.OnClickListener{
//        private Context context;
//        private ArrayList<TreeElement> treeElementlist;
//        private TreeAdapter treeAdapter;
//        private int position;
//
//        public TreeElementIconClickListener(Context mcontext,ArrayList<TreeElement> mtreeElementlist
//                                           ,TreeAdapter mtreeAdapter,int mposition ){
//            this.context = mcontext;
//            this.treeElementlist = mtreeElementlist;
//            this.treeAdapter = mtreeAdapter;
//            this.position = mposition;
//
//        }
//
//        @Override
//        public void onClick(View v) {
//            if(!treeElementlist.get(position).isMhasChild()){
//                Toast.makeText(context,
//                        treeElementlist.get(position).getOutlineTitle(),
//                        Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (treeElementlist.get(position).isExpanded()) {
//                treeElementlist.get(position).setExpanded(true);
//                TreeElement element = treeElementlist.get(position);
//                ArrayList<TreeElement> temp = new ArrayList<TreeElement>();
//
//                for (int i = position + 1; i < treeElementlist.size(); i++) {
//                    if (element.getLevel() >= treeElementlist.get(i).getLevel()) {
//                        break;
//                    }
//                    temp.add(treeElementlist.get(i));
//                }
//
//                treeElementlist.removeAll(temp);
//                for (int i = position + 1; i < treeElementlist.size(); i++) {
//                    System.out.println(treeElementlist.get(i).getOutlineTitle()
//                            + "@@@" + i);
//                    treeElementlist.get(i).getOutlineTitle();
//                }
//
//                treeAdapter.notifyDataSetChanged();
//            } else {
//                TreeElement obj = treeElementlist.get(position);
//                obj.setExpanded(true);
//                int level = obj.getLevel();
//                int nextLevel = level + 1;
//
//                ArrayList<TreeElement> tempList = obj.getChildList();
//              //一级级遍历
//                for (int i = 0; i < tempList.size(); i++) {
//                    TreeElement element = tempList.get(i);
//                    element.setLevel(nextLevel);
//                    element.setExpanded(true);
//                    treeElementlist.add(position + i + 1, element);
//                    for(TreeElement treeElement:element.getChildList()){
//                        treeElement.setLevel(nextLevel);
//                        treeElement.setExpanded(true);
//                        treeElementlist.add(position+1,treeElement);
//                    }
//                }
//                for (int i = position + 1; i < treeElementlist.size(); i++) {
//                    System.out.println(treeElementlist.get(i).getOutlineTitle()
//                            + "@@@" + i);
//                    treeElementlist.get(i).getOutlineTitle();
//                }
//                treeAdapter.notifyDataSetChanged();
//            }
//
//        }
//    }
}


