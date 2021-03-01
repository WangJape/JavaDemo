import React, { useState } from 'react';
import { ConfigProvider, DatePicker, message, Alert, Button } from 'antd';
import { DownloadOutlined } from '@ant-design/icons';

// 由于 antd 组件的默认文案是英文，所以需要修改为中文
import zhCN from 'antd/lib/locale/zh_CN';

export default function AntTest1(props) {
    const [date, setDate] = useState(null);

    //编程式路由
    const gotoView = () => {
        //传参方式一：restFul方式 :param 可以在props.match.params获取到{k:v}
        //props.history.push(`/route/${user.userName}/${user.role}`)

        //传参方式二: url传参,可以在props.location.search中获取?*=*&*=*,可以使用qs组件解析
        //props.history.push(`/route?userName=${user.userName}&role=${user.role}`)

        //传参方式三: state传参,可以在props.location.state获取到，注意：使用<HashRoute/>会导致 页面刷新丢失props,使用<BrowserRouter/>则不会
        const path = {
            pathname: '/route',
            state: {
                userName: user.userName,
                role: user.role
            }
        }
        props.history.push(path)
    }

    return (
        <ConfigProvider locale={zhCN}>
            <div>
                <Button type="primary" shape="circle"
                    icon={<DownloadOutlined />} onClick={gotoView}>
                    登录
                </Button>
            </div>
        </ConfigProvider>
    );
};
const user = {
    userName: 'jape',
    sex: 1,
    role: 'admin'
}
