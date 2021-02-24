import React from 'react'

export default class Clock extends React.Component {
    constructor(props) {
        super(props)
        this.state = { date: new Date() };
    }

    componentDidMount() {
        console.log("注册间隙定时器");
        this.timerID = setInterval(() => this.tick(), 1000);
    }
    componentWillUnmount() {
        console.log("清除间隙定时器");
        clearInterval(this.timerID);
    }
    componentDidUpdate() {
        console.log("时间更新");
    }

    tick() {
        this.setState({
            date: new Date()
        });
    }
    handleClick = () => {
        console.log(this)
    }

    // 强制更新
    force = () => {
        this.forceUpdate();
    }

    render() {
        return (
            <p onClick={this.handleClick}>{this.state.date.toLocaleTimeString()}</p>
        );
    }

    // 给组件的props加类型等限制
    static propTypes = {
    }
    // 指定默认标签属性值
    static defaultProps = {
    }
}

