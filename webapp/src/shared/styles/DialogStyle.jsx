import { PrimaryButtonStyle } from './ButtonStyle';

export const extendedPadding = '0 5rem';
export const extendedPaddingAll = '2rem 5rem';

const PAPER_ROOT = {
    borderRadius: 20,
    boxShadow: '0 10px 20px 0 rgba(0, 0, 0, 0.2)',
    backgroundColor: 'white',
    maxWidth: '50rem',
    width: '50rem',
};

export const DialogStyle = {
    backdrop: {
        backgroundColor: 'black',
        opacity: '.7 !important',
    },
    paperRoot: PAPER_ROOT,
    paperRootFull: Object.assign({}, PAPER_ROOT, {
        height: '55rem',
        maxHeight: 'calc(100% - 80px)',
    }),
    dialogBar: {
        display: 'flex',
        justifyContent: 'space-between',
        padding: extendedPaddingAll,
    },
    closeButton: {
        position: 'absolute',
        top: 0,
        right: 0,
    },
    closeIcon: {
        width: '2.4rem',
        height: '2.4rem',
    },
    dialogTitle: {
        padding: 0,
        '& h2': {
            fontSize: '3.5rem',
        }
    },
    dialogContent: {
        padding: extendedPadding,
    },
    dialogText: {
        padding: 0,
        fontSize: '1.6rem',
        color: '#000000',
    },
    dialogActionsRoot: {
        padding: extendedPaddingAll,
        display: 'flex',
        justifyContent: 'center',
    },
    dialogActions: {
        justifyContent: 'space-between',
        margin: '.8rem 0',
    },
    dialogActionLink: {
        width: '70%',
        fontSize: '1.6rem',
        fontWeight: 600,
        color: '#858585',
        pointerEvents: 'visible',
        cursor: 'pointer',
        textDecoration: 'none',
    },
    dialogAction: {
        margin: 0,
        ...PrimaryButtonStyle
    },
    autoWidth: {
        width: 'auto !important',
    },
    genericDialogContentFull: {
        padding: 0,
        overflow: 'hidden',
        margin: '2rem 0',
        height: '19rem',
    },
    genericDialogContent: {
        overflow: 'hidden',
        padding: extendedPadding,
    },
};