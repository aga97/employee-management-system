import { CircularProgress } from '@material-ui/core';
import axios from 'axios';
import React, { useEffect, useState } from 'react';


function InsertBack(props) {
    const [datas, setDatas] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        let unmounted = false;
        const fetchDatas = async () => {
            try {
                setDatas(null);
                setLoading(true);
                const res = await axios.post('http://localhost:3000/api/employee/create', props)
                if (!unmounted)
                    setDatas(res.data);
            } catch (e) {
                setError(e);
            }
            if (!unmounted) setLoading(false);
        };
        fetchDatas();
        return () => {//clean up      
            unmounted = true;
        }
    }, [props])
    if (loading) {
        return (
            <div >
                <CircularProgress color="secondary" />
            </div>
        )
    }
    if (error) {
        console.log(props)
        return <div>error</div>
    }
    if (!datas) return null;

    return (
        <div>
            {datas.seccess === true &&
                <div>
                    성공
                </div>
                /**
                 * 성공시 띄울거
                 * 실패시 띄울거...
                 */
            }
        </div>
    )
}

export default InsertBack;