import React from 'react'
import { ConfigProvider,Form, Input, InputNumber, Button, Checkbox, DatePicker } from 'antd';
import zhCN from 'antd/lib/locale/zh_CN';

export default class extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            account: "",
            password: "",
            age: {
                value: 0
            }
        };
    }

    componentDidMount() {
    }
    componentWillUnmount() {
    }
    componentDidUpdate() {
    }

    validatePrimeNumber(number) {

    }
    onNumberChange = value => {
        let validateStatus = 'success'
        let errorMsg = null
        if (value != 11) {
            validateStatus = 'error'
            errorMsg = '校验生效'
        }
        this.setState({
            age: {
                value: value,
                validateStatus: validateStatus,
                errorMsg: errorMsg
            }
        });
    };

    render() {
        return (
            <div>
                <ConfigProvider locale={zhCN}>
                    <Form
                        labelCol={{ span: 2 ,offset: 1}}
                        wrapperCol={{ span: 16 }}
                        name="test1"
                        initialValues={{ remember: true, age: this.state.age.value }}
                    >
                        <Form.Item label="账号" name="username" hasFeedback
                            rules={[{ required: true, pattern: "\\d{11}", message: '不符合规范' }]}
                        >
                            <Input allowClear placeholder=""/>
                        </Form.Item>

                        <Form.Item label="密码" name="password" hasFeedback
                            rules={[{ required: true, message: '不符合规范' }]}
                        >
                            <Input.Password allowClear/>
                        </Form.Item>

                        <Form.Item label="年龄" name="age" hasFeedback
                            rules={[{ required: true }]}
                            validateStatus={this.state.age.validateStatus}
                            help={this.state.age.errorMsg}
                        >
                            <InputNumber value={this.state.age.value} onChange={this.onNumberChange} />
                        </Form.Item>

                        <Form.Item name="date-time-picker" label="生日" >
                            <DatePicker showTime format="YYYY-MM-DD HH:mm:ss" />
                        </Form.Item>

                        <Form.Item name="remember" valuePropName="checked">
                            <Checkbox>记住我</Checkbox>
                        </Form.Item>

                        <Form.Item >
                            <Button type="primary" htmlType="submit">提交</Button>
                        </Form.Item>
                    </Form>
                </ConfigProvider>
            </div>
        );
    }
}