import React, { useState } from 'react';
import { ConfigProvider, DatePicker, message, Alert } from 'antd';

// 由于 antd 组件的默认文案是英文，所以需要修改为中文
import zhCN from 'antd/lib/locale/zh_CN';

export default function AntTest1() {
    const [date, setDate] = useState(null);


    return (
        <ConfigProvider locale={zhCN}>
            <div>
                登录
            </div>
        </ConfigProvider>
    );
};
