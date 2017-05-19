### EasyRefreshView

EasyRefreshView is based on RecyclerView and SwipeRefreshLayout for developers to easily implements pull refresh and drop refresh list widget
***
#### Usage
1. Include the library as local library project.
>compile 'com.ray.easyrefreshview:easy-refresh-view:0.5.9'

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

4. Customize the layout with bottom status

#### Feedback
- Email: guoray@yeah.net

#### ChangeLog
***
##### Version 0.5.9
- Initial Build
#### License
***
> Copyright 2017, Ray.Guo

>Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

>   http://www.apache.org/licenses/LICENSE-2.0

>Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
