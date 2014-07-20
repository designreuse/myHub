package kr.co.myhub.app.common.login.domain;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import javax.annotation.Nullable;

import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathInits;
import com.mysema.query.types.path.StringPath;

/**
 * 
 * file   : QLogHistory.java
 * date   : 2014. 7. 20.
 * author : jmpark
 * content: 로그이력 쿼리 타입 
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 7. 20.   kbtapjm     최초생성
 */
public class QLogHistory extends EntityPathBase<LogHistory> {

    private static final long serialVersionUID = -8676585085793068471L;
    
    private static final PathInits INITS = PathInits.DIRECT;
    
    public static final QLogHistory logHistory = new QLogHistory("logHistory");
    
    public final NumberPath<Long> logHistoryKey = createNumber("logHistoryKey", Long.class);
    
    public final StringPath email = createString("email");
    
    public final StringPath ipAddress = createString("ipAddress");
    
    public final DateTimePath<java.util.Date> logDate = createDateTime("logDate", java.util.Date.class);
    
    public final StringPath logType = createString("logType");
    
    public final NumberPath<Long> userKey = createNumber("userKey", Long.class);
    
    public final StringPath sessionId = createString("sessionId");
    
    public QLogHistory(String variable) {
        this(LogHistory.class, forVariable(variable), INITS);
    }

    public QLogHistory(Class<? extends LogHistory> type, PathMetadata<?> metadata) {
        super(type, metadata);
    }
    
    public QLogHistory(PathMetadata<?> metadata, PathInits inits) {
        this(LogHistory.class, metadata, inits);
    }

    private QLogHistory(Class<? extends LogHistory> type, PathMetadata<?> metadata,
            @Nullable PathInits inits) {
        super(type, metadata, inits);
    }

    private QLogHistory(Class<? extends LogHistory> type, String variable) {
        super(type, variable);
    }

}
