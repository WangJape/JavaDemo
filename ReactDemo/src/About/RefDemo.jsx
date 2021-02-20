import React from 'react'

export default class RefDemo extends React.Component {
    constructor(props) {
        super(props)
    }
    state = {
    }
    myRef = React.createRef()
    componentDidMount() {
    }
    componentWillUnmount() {
    }
    componentDidUpdate() {
    }
    handleClick = () => {
        console.log(this.refs.input1.value)
        console.log(this.input2.value)
        console.log(this.input3.value)
        console.log(this.myRef.current.value)
    }
    saveInput = (cur) => {
        this.input3 = cur
    }

    render() {
        return (
            // ref的三种方式
            //1 、字符串(过时性能低)
            //2、回调(推荐)、class的函数(避免视图更新时二次调用内联函数，无所谓)
            //3、React.createRef生成一个容器，存储ref标识的元素,但只能存一个,想用多个就多createRef
            <div>
                <input ref="input1" onBlur={this.handleClick} />
                <input ref={cur => this.input2 = cur} />
                <input ref={this.saveInput} />
                <input ref={this.myRef} />
                <button onClick={this.handleClick}>点击</button>
            </div>
        );
    }
}

