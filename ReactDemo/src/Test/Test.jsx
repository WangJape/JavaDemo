import React from 'react'
import { Form, Input, Checkbox, DatePicker } from 'antd';
import moment from 'moment';

export default class Test extends React.Component {
    state = {
        startDate: null,
        endDate: null,
        duration: 30,
        def30Day: true,
    }
    startDateChange = (date, dateString) => {
        this.setState({
            startDate: dateString
        }, () => this.updateEndDate())
    }
    durationChange = e => {
        let def30 = false
        if (e.target.value == 30) {
            def30 = true
        }
        this.setState({
            duration: e.target.value,
            def30Day: def30
        }, () => this.updateEndDate())
    }
    defChange = e => {
        this.setState({
            def30Day: e.target.checked,
        });
        if (e.target.checked) {
            this.setState({
                duration: 30,
            }, () => this.updateEndDate())
        }

    }
    updateEndDate = () => {
        console.log(this.state.startDate)
        const startDate = new Date(this.state.startDate)
        const duration = this.state.duration
        const endDateStr = moment(startDate).add(duration, 'days').format('YYYY-MM-DD')
        console.log(endDateStr)
        this.setState({
            endDate: endDateStr
        })
    }
    render() {
        return (
            <div>
                <Form labelCol={{ span: 2, offset: 1 }} wrapperCol={{ span: 5 }} name="test1">
                    <Form.Item label="开始时间">
                        <DatePicker onChange={this.startDateChange} />
                    </Form.Item>
                    <Form.Item label="有效期限">
                        <Input value={this.state.duration} onChange={this.durationChange} />
                        <Checkbox checked={this.state.def30Day} onChange={this.defChange}>默认30天</Checkbox>
                    </Form.Item>
                    <Form.Item label="结束时间">
                        <DatePicker value={moment(this.state.endDate, 'YYYY-MM-DD')} />
                    </Form.Item>
                </Form>
            </div>
        );
    }
}