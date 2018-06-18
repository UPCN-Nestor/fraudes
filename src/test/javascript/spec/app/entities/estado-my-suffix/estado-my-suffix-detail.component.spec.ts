/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FrTestModule } from '../../../test.module';
import { EstadoMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/estado-my-suffix/estado-my-suffix-detail.component';
import { EstadoMySuffixService } from '../../../../../../main/webapp/app/entities/estado-my-suffix/estado-my-suffix.service';
import { EstadoMySuffix } from '../../../../../../main/webapp/app/entities/estado-my-suffix/estado-my-suffix.model';

describe('Component Tests', () => {

    describe('EstadoMySuffix Management Detail Component', () => {
        let comp: EstadoMySuffixDetailComponent;
        let fixture: ComponentFixture<EstadoMySuffixDetailComponent>;
        let service: EstadoMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [EstadoMySuffixDetailComponent],
                providers: [
                    EstadoMySuffixService
                ]
            })
            .overrideTemplate(EstadoMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EstadoMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstadoMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EstadoMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.estado).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
