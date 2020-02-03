import React from 'react';

const notFoundImg = `${process.env.PUBLIC_URL}/img/containers/notfound/not_found.png`;

export const NotFound = () => (
    <div className="App">
        <div className="App__info__wrapper not__found">
            <h2 className="Not__found__title">
                404 Not Found
            </h2>
            <div className="Not__found__img">
                <img src={notFoundImg} className="App__ic ic__free__trial" alt="FreeTrial" />
            </div>
        </div>
    </div>
);
