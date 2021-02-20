import React, { useState } from 'react';
import { ConfigProvider, DatePicker, message, Alert } from 'antd';

// 由于 antd 组件的默认文案是英文，所以需要修改为中文
import zhCN from 'antd/lib/locale/zh_CN';


export default function AntTest1(props) {
    const [date, setDate] = useState(null);

    const handleChange = (value)=>{
        //props.history.push('/index/about')
        message.info(`您选择的日期是: ${value ? value.format('YYYY年MM月DD日') : '未选择'}`);
        setDate(value);
    }
    return (
        <ConfigProvider locale={zhCN}>
        <div>
            <DatePicker onChange={value => handleChange(value)} />
            <Alert message="当前日期" description={date ? date.format('YYYY年MM月DD日') : '未选择'} />
        </div>
        </ConfigProvider>
    );
};