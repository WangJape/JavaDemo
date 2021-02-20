import React, { useState, useEffect } from 'react';

export default function About(props) {

    // 声明一个叫 "count" 的 state 变量，赋初值0
    const [count, setCount] = useState(0);

    // 等同于 componentDidMount componentDidUpdate componentDidUpdate三个函数的组合
    useEffect(() => {
        console.log(`You clicked ${count} times`);
        return () => {
            console.log("清除 effect");
        };
    }, []);

    return (
        <div>
            <button onClick={() => setCount(count + 1)}>加1</button>
            <p>关于{count}</p>
        </div>
    )
}