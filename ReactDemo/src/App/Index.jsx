import React, { useState } from 'react';
import './Index.css'
import { Layout, Menu, Breadcrumb } from 'antd';

import { BrowserRouter, Link, NavLink, Route, Switch } from 'react-router-dom'
import Clock from '../Components/Clock';
import About from '../About/About';
import RefDemo from '../About/RefDemo';
import AntTest1 from '../Ant/AntTest1';
import FormTest from '../Ant/FormTest';
import Test from '../Test/Test';

const { Header, Content, Footer } = Layout;

export default function App() {
    return (
        <Layout>
            <Header style={{ position: 'fixed', zIndex: 1, width: '100%' }}>
                <div className="logo" />
                <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']}>
                    <Menu.Item key="1">
                        <NavLink to="/index/antTest1">蚂蚁1</NavLink>
                    </Menu.Item>
                    <Menu.Item key="2">
                        <NavLink to="/index/antForm">表单</NavLink>
                    </Menu.Item>
                    <Menu.Item key="/index/clock">
                        <NavLink to="/index/clock">时钟</NavLink>
                    </Menu.Item>
                    <Menu.Item key="/index/about">
                        <NavLink to="/index/about">关于</NavLink>
                    </Menu.Item>
                    <Menu.Item key="/index/ref">
                        <NavLink to="/index/ref">引用</NavLink>
                    </Menu.Item>
                    <Menu.Item key="/index/test">
                        <NavLink to="/index/test">测试</NavLink>
                    </Menu.Item>
                </Menu>
            </Header>

            <Content className="site-layout" style={{ padding: '0 30px', marginTop: 64 }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item>Home</Breadcrumb.Item>
                    <Breadcrumb.Item>List</Breadcrumb.Item>
                    <Breadcrumb.Item>App</Breadcrumb.Item>
                </Breadcrumb>
                <div className="site-layout-background" style={{ padding: 10, minHeight: 450 }}>
                    <Switch>
                        <Route path="/index/antTest1" component={AntTest1} />
                        <Route path="/index/antForm" component={FormTest} />
                        <Route path="/index/clock" component={Clock} />
                        <Route path="/index/about" component={About} />
                        <Route path="/index/ref" component={RefDemo} />
                        <Route path="/index/test" component={Test} />
                    </Switch>
                </div>
            </Content>

            <Footer style={{ textAlign: 'center' }}>Ant Design ©2018 Created by Ant UED</Footer>
        </Layout>
    );
}