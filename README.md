## EasyRefreshView

EasyRefreshView is based on RecyclerView and SwipeRefreshLayout for developers to easily implements pull refresh and drop refresh list widget

## Screenshots

<table><tr>
<td><img src="drop_down.gif" width="400px" height="600px" border=0></td>
<td><img src="pull_up.gif" width="400px" height="600px" border=0></td>
</tr></table>

## Usage
1. Include the library as local library project.

        compile 'com.ray.easyrefreshview:easy-refresh-view:0.5.9'

2. Customize the Itemview display

        easyRefreshView.bindAdapter(new EasyRefreshHolderCallBack() {
            @Override
            public RecyclerView.ViewHolder onCreateNormal(View view) {
                //return new NormalHolder(view);
            }

            @Override
            public void onBindNormal(RecyclerView.ViewHolder holder, int position) {
                super.onBindNormal(holder, position);
                //todo
            }
        });

3. Refresh view with the data from callback

        public void onTopLoadStarted() {
            list.clear();
            startRxLoad(0);
        }

        public void onBottomLoadStarted(int position) {
            if (position >= mTotalCount) {
                erv.setFootViewState(EasyRefreshAdapter.FOOT_STATE_LOAD_NOMORE);
                return;
            }
            erv.setFootViewState(EasyRefreshAdapter.FOOT_STATE_LOADING);
            startRxLoad(position + 1);
        }

4. Customize the layout with bottom status

        easyrefreshview:layoutType="linear"
        easyrefreshview:normalLayout="@layout/item_normal"
        easyrefreshview:loadingLayout="@layout/foot_loading"
        easyrefreshview:nomoreLayout="@layout/foot_loading_nomore_data"
        easyrefreshview:errorLayout="@layout/foot_loading_error"

## Feedback
* Email: guoray@yeah.net

## Blog
* http://www.jianshu.com/u/e80ee485cfce

## ChangeLog

#### Version 0.5.9
* Initial Build

## License

    Copyright 2017, Ray.Guo

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
